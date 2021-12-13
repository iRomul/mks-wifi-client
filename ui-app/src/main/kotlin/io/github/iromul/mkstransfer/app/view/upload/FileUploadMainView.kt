package io.github.iromul.mkstransfer.app.view.upload

import io.github.iromul.mkstransfer.app.controller.PrinterController
import tornadofx.View
import tornadofx.addChildIfPossible
import tornadofx.hbox
import tornadofx.onChange
import tornadofx.useMaxSize

class FileUploadMainView : View() {

    private val fileSelectionView by inject<FileSelectionView>()
    private val filePreviewView by inject<FilePreviewView>()
    private val printerController by inject<PrinterController>()

    override val root = hbox {
        useMaxSize = true

        addChildIfPossible(fileSelectionView.root)

        val fileToUpload = printerController.selectedFile

        fileToUpload.hasFileProperty.onChange { hasFile ->
            if (hasFile) {
                children.remove(fileSelectionView.root)
                addChildIfPossible(filePreviewView.root)
            } else {
                children.remove(filePreviewView.root)
                addChildIfPossible(fileSelectionView.root)
            }
        }
    }
}