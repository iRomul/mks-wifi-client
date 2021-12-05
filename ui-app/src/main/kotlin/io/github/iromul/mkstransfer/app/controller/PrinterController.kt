package io.github.iromul.mkstransfer.app.controller

import io.github.iromul.mkstransfer.app.io.gcode.GCodeFileReader
import io.github.iromul.mkstransfer.app.io.gcode.thumbnails.MksTftThumbnailWriter
import io.github.iromul.mkstransfer.app.io.gcode.thumbnails.PrusaSlicerEmbeddedThumbnailReader
import io.github.iromul.mkstransfer.app.io.gcode.thumbnails.Size
import io.github.iromul.mkstransfer.app.model.FileToUpload
import io.github.iromul.mkstransfer.app.model.PrinterSettingsModel
import io.github.iromul.mkstransfer.app.model.PrinterStatus
import io.github.iromul.mkstransfer.app.service.SendService
import mu.KLogging
import tornadofx.Controller
import tornadofx.Rest
import java.io.File
import java.io.StringWriter
import java.net.InetAddress

class PrinterController : Controller() {

    val printerSettings = PrinterSettingsModel()
    val printerStatus = PrinterStatus("NOT_CONNECTED")

    val fileToUpload = FileToUpload()

    val api: Rest by inject()

    val sendService by di<SendService>()

    fun testConnection() {
        val ipAddress = printerSettings.ipAddress.value

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

        with(fileToUpload) {
            fileName = file.name
            fileData = fileBytes

            val gCodeFile = GCodeFileReader.load(fileBytes)

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

                GCodeFileReader.save(gCodeFile, stringWriter)

                modifiedFileData = stringWriter.toString().toByteArray()
            }

            hasFile = true

            logger.info { "Loaded GCode file $file (has ${prusaThumbnails.size} thumbnails)" }
        }
    }

    fun cancelFileUpload() {
        fileToUpload.clearFile()
    }

    fun uploadFile() {
        val address = "http://${printerSettings.ipAddress.get()}:80/upload?X-Filename=${fileToUpload.fileName}"

        logger.info { "Sending GCode to $address" }

        val r = api.post(address, fileToUpload.actualFileToUpload.inputStream()) {
            it.addHeader("Content-Type", "application/octet-stream")
            it.addHeader("Connection", "keep-alive")
        }

        try {
            if (r.ok()) {
                logger.info { "File sent successfully!" }
            } else {
                logger.error { "Service invocation is failed with code ${r.statusCode} and message: ${r.reason}" }
            }
        } finally {
            r.consume()
        }

        sendService.send()
    }

    private companion object : KLogging()
}