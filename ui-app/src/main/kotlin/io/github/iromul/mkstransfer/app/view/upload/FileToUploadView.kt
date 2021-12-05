package io.github.iromul.mkstransfer.app.view.upload

import io.github.iromul.commons.javafx.bindings.asBinaryUnit
import io.github.iromul.commons.javafx.opacity
import io.github.iromul.commons.lang.requireResource
import io.github.iromul.mkstransfer.app.controller.PrinterController
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.paint.Color
import tornadofx.RestProgressBar
import tornadofx.View
import tornadofx.action
import tornadofx.assignIfNull
import tornadofx.button
import tornadofx.buttonbar
import tornadofx.field
import tornadofx.fieldset
import tornadofx.form
import tornadofx.gridpane
import tornadofx.imageview
import tornadofx.stackpane
import tornadofx.stackpaneConstraints
import tornadofx.style
import tornadofx.text
import tornadofx.textfield
import tornadofx.titledpane
import tornadofx.useMaxSize
import tornadofx.vbox

class FileToUploadView : View() {

    private val printerController by inject<PrinterController>()
    private val defaultImage = Image(requireResource("/images/no_preview.png") {}.toExternalForm())

    override val root = gridpane {
        val fileToUpload = printerController.fileToUpload

        useMaxSize = true
        alignment = Pos.CENTER

        vbox {
            stackpane {
                val image = fileToUpload.fileThumbnailProperty
                    .also {
                        it.assignIfNull { defaultImage }
                    }

                imageview(image) {
                    useMaxSize = true
                }

                text(fileToUpload.fileNameProperty) {
                    stackpaneConstraints {
                        alignment = Pos.TOP_LEFT
                    }

                    style {
                        backgroundColor += Color.BLACK.opacity(0.5)
                    }
                }

                text(fileToUpload.fileSizeProperty.asBinaryUnit()) {
                    stackpaneConstraints {
                        alignment = Pos.BOTTOM_RIGHT
                    }

                    style {
                        backgroundColor += Color.BLACK.opacity(0.5)
                    }
                }
            }

            titledpane(title = "Upload via Wi-Fi", collapsible = false) {
                form {
                    fieldset {
                        field("Target file name") {
                            textfield(fileToUpload.fileNameProperty)
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
}