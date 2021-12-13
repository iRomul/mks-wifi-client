package io.github.iromul.mkstransfer.app.view.upload

import io.github.iromul.mkstransfer.app.controller.PrinterController
import javafx.beans.property.SimpleBooleanProperty
import tornadofx.View
import tornadofx.action
import tornadofx.addChildIfPossible
import tornadofx.button
import tornadofx.getValue
import tornadofx.onChange
import tornadofx.pane
import tornadofx.setValue
import tornadofx.useMaxSize
import tornadofx.vbox
import tornadofx.visibleWhen

class FileUploadMainView : View() {

    private val fileSelectionView by inject<FileSelectionView>()
    private val filePreviewView by inject<FilePreviewView>()
    private val printerController by inject<PrinterController>()

    private val showBackProperty = SimpleBooleanProperty(false)
    private var showBack by showBackProperty

    override val root = vbox {
        useMaxSize = true

        addChildIfPossible(fileSelectionView.root)

        pane {
            visibleWhen(showBackProperty)

            button("<- back").action {
                showBack = false
            }
        }

        val fileToUpload = printerController.selectedFile

        fileToUpload.hasFileProperty.onChange { hasFile ->
            if (hasFile) {
                showBack = true

                children.remove(fileSelectionView.root)
                addChildIfPossible(filePreviewView.root)
            } else {
                showBack = false

                children.remove(filePreviewView.root)
                addChildIfPossible(fileSelectionView.root)
            }
        }
    }
}