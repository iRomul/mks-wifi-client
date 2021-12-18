package io.github.iromul.mkstransfer.app.view.upload

import io.github.iromul.commons.javafx.bindings.asBinaryUnit
import io.github.iromul.commons.lang.requireResource
import io.github.iromul.commons.tornadofx.labeledseparator
import io.github.iromul.mkstransfer.app.controller.PrinterController
import io.github.iromul.mkstransfer.app.model.settings.printer.PrinterSettingsModel
import io.github.iromul.mkstransfer.app.model.upload.UploadStatus
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import tornadofx.RestProgressBar
import tornadofx.View
import tornadofx.action
import tornadofx.assignIfNull
import tornadofx.button
import tornadofx.buttonbar
import tornadofx.enableWhen
import tornadofx.field
import tornadofx.fieldset
import tornadofx.form
import tornadofx.hboxConstraints
import tornadofx.hgrow
import tornadofx.imageview
import tornadofx.label
import tornadofx.scrollpane
import tornadofx.stringBinding
import tornadofx.text
import tornadofx.textfield
import tornadofx.useMaxSize
import tornadofx.vbox
import tornadofx.visibleWhen

class FilePreviewView : View() {

    private val printerController by inject<PrinterController>()
    private val printerSettings by inject<PrinterSettingsModel>()
    private val defaultImage = Image(requireResource("/images/no_preview.png") {}.toExternalForm())

    override val root = scrollpane(fitToWidth = true) {
        vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED

        hboxConstraints {
            hgrow = Priority.ALWAYS
        }

        vbox {
            val fileToUpload = printerController.selectedFile
            val fileUploadStatus = printerController.fileUploadStatus

            form {
                labeledseparator("G-code file")

                fieldset {
                    field("Filename") {
                        label(fileToUpload.fileNameProperty)
                    }
                }

                fieldset {
                    field("Size") {
                        label(fileToUpload.fileSizeProperty.asBinaryUnit())
                    }
                }

                fieldset {
                    field("Thumbnails") {
                        val image = fileToUpload.fileThumbnailProperty
                            .also {
                                it.assignIfNull { defaultImage }
                            }

                        imageview(image) {
                            useMaxSize = true
                        }
                    }
                }
            }

            form {
                visibleWhen(printerSettings.isMksTftHostUploadMode)
                enableWhen(printerSettings.isMksTftHostUploadMode)

                labeledseparator("Upload via Wi-Fi")

                fieldset {
                    field("Target file name") {
                        textfield(fileToUpload.fileNameProperty)
                    }

                    field("Upload status") {
                        visibleWhen(fileUploadStatus.statusProperty.isNotEqualTo(UploadStatus.IDLE))

                        val uploadStatusText = fileUploadStatus.statusProperty.stringBinding {
                            when (it) {
                                UploadStatus.IDLE -> "Idle"
                                UploadStatus.UPLOADING -> "Uploading..."
                                UploadStatus.SUCCESS -> "File successfully uploaded"
                                UploadStatus.FAILED -> "File uploading failed: ${fileUploadStatus.error}"
                                null -> "Error"
                            }
                        }

                        text(uploadStatusText)
                    }
                }

                buttonbar {
                    button("Cancel").action {
                        printerController.cancelFileUpload()
                    }

                    button("Send").action {
                        runAsync {
                            printerController.uploadFile()
                        }
                    }
                }

                add(RestProgressBar::class)
            }
        }
    }
}