package io.github.iromul.mkstransfer.app.view.upload

import io.github.iromul.commons.javafx.bindings.asBinaryUnit
import io.github.iromul.commons.javafx.materialdesign.icons.MaterialIcons
import io.github.iromul.commons.javafx.materialdesign.icons.MaterialIconsOutlined
import io.github.iromul.commons.lang.requireResource
import io.github.iromul.mkstransfer.app.controller.PrinterController
import io.github.iromul.mkstransfer.app.model.settings.printer.PrinterSettingsModel
import io.github.iromul.mkstransfer.app.model.upload.UploadStatus
import io.github.iromul.mkstransfer.app.view.styles.MainStylesheet
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Orientation
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import tornadofx.FileChooserMode.Save
import tornadofx.RestProgressBar
import tornadofx.View
import tornadofx.action
import tornadofx.addClass
import tornadofx.assignIfNull
import tornadofx.button
import tornadofx.buttonbar
import tornadofx.c
import tornadofx.chooseFile
import tornadofx.field
import tornadofx.fieldset
import tornadofx.form
import tornadofx.getValue
import tornadofx.hbox
import tornadofx.hboxConstraints
import tornadofx.hgrow
import tornadofx.imageview
import tornadofx.label
import tornadofx.managedWhen
import tornadofx.multi
import tornadofx.scrollpane
import tornadofx.separator
import tornadofx.setValue
import tornadofx.stringBinding
import tornadofx.style
import tornadofx.text
import tornadofx.textfield
import tornadofx.toolbar
import tornadofx.useMaxSize
import tornadofx.vbox
import tornadofx.visibleWhen

class FilePreviewView : View() {

    private val printerController by inject<PrinterController>()
    private val printerSettings by inject<PrinterSettingsModel>()
    private val defaultImage = Image(requireResource("/images/no_preview.png") {}.toExternalForm())

    val fileToUpload = printerController.selectedFile
    val fileUploadStatus = printerController.fileUploadStatus

    private val isSendPaneOpenProperty = SimpleBooleanProperty(false)
    private var isSendPaneOpen by isSendPaneOpenProperty

    override val root = vbox {
        hboxConstraints {
            hgrow = Priority.ALWAYS
        }

        toolbar {
            addClass(MainStylesheet.nav)

            button(graphic = Label(MaterialIconsOutlined.arrowBack)) {
                addClass(MaterialIconsOutlined.className)
                addClass(MainStylesheet.navButton, MainStylesheet.navButtonIcon)
                action {
                    fileToUpload.clearFile()
                    FileUploadMainViewData.viewState = FileUploadMainViewState.FILE_SELECTION
                }
            }

            separator()

            label(fileToUpload.fileNameProperty)
        }

        toolbar {
            addClass(MainStylesheet.nav)

            style {
                backgroundColor = multi(c("#2b2b2b"))
            }

            button(graphic = Label(MaterialIcons.save)) {
                addClass(MaterialIcons.className)
                addClass(MainStylesheet.navButton, MainStylesheet.navButtonIcon)

                action {
                    val targetFile = chooseFile(
                        "Save gcode file as",
                        filters = arrayOf(AllowedGCCodeExtensions.fileChooserFilter),
                        mode = Save
                    ) {
                        initialFileName = fileToUpload.fileName
                    }

                    if (targetFile.isNotEmpty()) {
                        printerController.saveToFile(targetFile.first())
                    }
                }
            }

            button(graphic = Label(MaterialIcons.cloudUpload)) {
                managedWhen(printerSettings.isMksTftHostUploadMode)

                addClass(MaterialIcons.className)
                addClass(MainStylesheet.navButton, MainStylesheet.navButtonIcon)

                action {
                    isSendPaneOpen = !isSendPaneOpen
                }
            }
        }

        toolbar {
            managedWhen(isSendPaneOpenProperty)

            addClass(MainStylesheet.nav)

            style {
                backgroundColor = multi(c("#2b2b2b"))
            }

            form {
                fieldset {
                    field("File name") {
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
                    add(RestProgressBar::class)

                    button("Cancel").action {
                        printerController.cancelFileUpload()
                    }

                    button("Send").action {
                        runAsync {
                            printerController.uploadFile()
                        }
                    }
                }
            }
        }

        scrollpane(fitToWidth = true, fitToHeight = true) {
            vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
            isPannable = true

            addClass(MainStylesheet.fancyScroll)

            vbox {
                style {
                    backgroundColor = multi(c("#2b2b2b"))
                }

                hbox {
                    form {
                        fieldset {
                            field("Size") {
                                label(fileToUpload.fileSizeProperty.asBinaryUnit())
                            }
                        }

                        fieldset {
                            field("Lines") {
                                label(fileToUpload.fileLinesProperty)
                            }
                        }

                        fieldset {
                            field("Material") {
                                label(fileToUpload.materialTypeProperty)
                            }
                        }
                    }

                    form {
                        fieldset(labelPosition = Orientation.VERTICAL) {
                            field("Thumbnails", orientation = Orientation.VERTICAL) {
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
                }
            }
        }
    }
}