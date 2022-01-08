package io.github.iromul.mkstransfer.app.view.upload

import io.github.iromul.mkstransfer.app.controller.PrinterController
import io.github.iromul.mkstransfer.app.view.upload.FileUploadMainViewData.viewState
import io.github.iromul.mkstransfer.app.view.upload.FileUploadMainViewData.viewStateProperty
import javafx.scene.layout.Priority
import tornadofx.View
import tornadofx.addChildIfPossible
import tornadofx.hbox
import tornadofx.onChange
import tornadofx.useMaxSize
import tornadofx.vbox
import tornadofx.vboxConstraints
import tornadofx.vgrow

class FileUploadMainView : View() {

    private val fileSelectionView by inject<FileSelectionView>()
    private val filePreviewView by inject<FilePreviewView>()
    private val printerController by inject<PrinterController>()

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