package io.github.iromul.mkstransfer.app.view.upload

import io.github.iromul.commons.javafx.materialdesign.icons.MaterialIconsOutlined
import io.github.iromul.mkstransfer.app.controller.PrinterController
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Label
import javafx.scene.layout.Priority
import tornadofx.View
import tornadofx.action
import tornadofx.addChildIfPossible
import tornadofx.addClass
import tornadofx.button
import tornadofx.getValue
import tornadofx.hbox
import tornadofx.onChange
import tornadofx.setValue
import tornadofx.useMaxSize
import tornadofx.vbox
import tornadofx.vboxConstraints
import tornadofx.vgrow
import tornadofx.visibleWhen

class FileUploadMainView : View() {

    private val fileSelectionView by inject<FileSelectionView>()
    private val filePreviewView by inject<FilePreviewView>()
    private val printerController by inject<PrinterController>()

    private val viewStateProperty = SimpleObjectProperty(FileUploadMainViewState.INITIAL)
    private var viewState by viewStateProperty

    override val root = vbox {
        useMaxSize = true

        val fileToUpload = printerController.selectedFile

        fileToUpload.hasFileProperty.onChange { hasFile ->
            viewState = if (hasFile) {
                FileUploadMainViewState.FILE_PREVIEW
            } else {
                FileUploadMainViewState.FILE_SELECTION
            }
        }

        hbox {
            managedProperty().bind(visibleProperty())
            visibleWhen(viewStateProperty.isNotEqualTo(FileUploadMainViewState.FILE_SELECTION))

            button(text = "Back", graphic = Label(MaterialIconsOutlined.arrowBack).apply { addClass(MaterialIconsOutlined.className) }) {
                action {
                    fileToUpload.clearFile()
                    viewState = FileUploadMainViewState.FILE_SELECTION
                }
            }
        }

        hbox {
            vboxConstraints {
                vgrow = Priority.ALWAYS
            }

            viewStateProperty.onChange { state ->
                children.clear()

                when (state) {
                    FileUploadMainViewState.FILE_SELECTION -> addChildIfPossible(fileSelectionView.root)
                    FileUploadMainViewState.FILE_PREVIEW -> addChildIfPossible(filePreviewView.root)
                    else -> error("Impossible transition")
                }
            }
        }

        viewState = FileUploadMainViewState.FILE_SELECTION
    }
}