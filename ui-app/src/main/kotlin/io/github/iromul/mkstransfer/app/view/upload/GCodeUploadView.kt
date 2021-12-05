package io.github.iromul.mkstransfer.app.view.upload

import io.github.iromul.mkstransfer.app.controller.PrinterController
import tornadofx.View
import tornadofx.addChildIfPossible
import tornadofx.hbox
import tornadofx.onChange
import tornadofx.useMaxSize

class GCodeUploadView : View() {

    private val dragAndDropView by inject<DragAndDropView>()
    private val fileToUploadView by inject<FileToUploadView>()
    private val printerController by inject<PrinterController>()

    override val root = hbox {
        useMaxSize = true

        addChildIfPossible(dragAndDropView.root)

        val fileToUpload = printerController.fileToUpload

        fileToUpload.hasFileProperty.onChange { hasFile ->
            if (hasFile) {
                children.remove(dragAndDropView.root)
                addChildIfPossible(fileToUploadView.root)
            } else {
                children.remove(fileToUploadView.root)
                addChildIfPossible(dragAndDropView.root)
            }
        }
    }
}