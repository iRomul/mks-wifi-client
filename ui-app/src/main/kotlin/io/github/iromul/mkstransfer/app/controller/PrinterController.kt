package io.github.iromul.mkstransfer.app.controller

import io.github.iromul.gcode.file.GCodeIO
import io.github.iromul.mkstransfer.app.io.gcode.thumbnails.MksTftThumbnailWriter
import io.github.iromul.mkstransfer.app.io.gcode.thumbnails.PrusaSlicerEmbeddedThumbnailReader
import io.github.iromul.mkstransfer.app.io.gcode.thumbnails.Size
import io.github.iromul.mkstransfer.app.model.PrinterStatus
import io.github.iromul.mkstransfer.app.model.settings.printer.MksTftUploadSettingsModel
import io.github.iromul.mkstransfer.app.model.settings.printer.PrinterSettingsModel
import io.github.iromul.mkstransfer.app.model.upload.FileUploadStatus
import io.github.iromul.mkstransfer.app.model.upload.SelectedFile
import io.github.iromul.mkstransfer.app.model.upload.UploadStatus
import io.github.iromul.mkstransfer.app.service.SendService
import mu.KLogging
import tornadofx.Controller
import tornadofx.Rest
import java.io.File
import java.io.StringWriter
import java.net.InetAddress

class PrinterController : Controller() {

    private val printerSettings by inject<PrinterSettingsModel>()
    private val mksTftUploadSettings by inject<MksTftUploadSettingsModel>()

    val printerStatus = PrinterStatus("NOT_CONNECTED")

    val selectedFile = SelectedFile()
    val fileUploadStatus = FileUploadStatus(UploadStatus.IDLE, null)

    val api: Rest by inject()

    val sendService by di<SendService>()

    fun testConnection() {
        val printerSettings = printerSettings

        val ipAddress = printerSettings.socketAddress.value

        runCatching {
            InetAddress.getByName(ipAddress)
        }.exceptionOrNull()?.also {
            printerStatus.status = "UNREACHEABLE"

            throw IllegalStateException("Can't connect printer using address: $ipAddress", it)
        } ?: run {
            printerStatus.status = "CONNECTED"
        }
    }

    fun setFileToUpload(file: File) {
        val fileBytes = file.readBytes()

        with(selectedFile) {
            fileName = file.name
            fileData = fileBytes

            val gCodeFile = GCodeIO.buffered(fileBytes)

            val prusaThumbnails = PrusaSlicerEmbeddedThumbnailReader(gCodeFile).getAllThumbnails()

            if (prusaThumbnails.isNotEmpty()) {
                prusaThumbnails
                    .maxByOrNull { it.width * it.height }
                    ?.also {
                        fileThumbnail = it
                    }

                gCodeFile.trimHead()

                MksTftThumbnailWriter(gCodeFile, Size(50, 50), Size(200, 200))
                    .writeAllThumbnails(prusaThumbnails)

                val stringWriter = StringWriter()

                GCodeIO.write(gCodeFile, stringWriter)

                precessedFileData = stringWriter.toString().toByteArray()
            }

            hasFile = true

            logger.info { "Loaded GCode file $file (has ${prusaThumbnails.size} thumbnails)" }
        }
    }

    fun cancelFileUpload() {
        fileUploadStatus.status = UploadStatus.IDLE
        fileUploadStatus.error = null

        selectedFile.clearFile()
    }

    fun uploadFile() {
        val address = "http://${mksTftUploadSettings.mksUploadAddress.get()}/upload?X-Filename=${selectedFile.fileName}"

        logger.info { "Sending G-code to $address" }

        fileUploadStatus.status = UploadStatus.UPLOADING

        val r = api.post(address, selectedFile.actualFileToUpload.inputStream()) {
            it.addHeader("Content-Type", "application/octet-stream")
            it.addHeader("Connection", "keep-alive")
        }

        try {
            if (r.ok()) {
                logger.info { "File sent successfully!" }

                fileUploadStatus.status = UploadStatus.SUCCESS
            } else {
                logger.error { "Service invocation is failed with code ${r.statusCode} and message: ${r.reason}" }

                fileUploadStatus.status = UploadStatus.FAILED
                fileUploadStatus.error = "${r.statusCode}: ${r.reason}"
            }
        } catch (e: Throwable) {
            fileUploadStatus.status = UploadStatus.FAILED
            fileUploadStatus.error = e.message
        } finally {
            r.consume()
        }

        sendService.send()
    }

    private companion object : KLogging()
}