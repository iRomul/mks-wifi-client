package io.github.iromul.mkstransfer.app.view.upload

import io.github.iromul.mkstransfer.app.controller.PrinterController
import io.github.iromul.mkstransfer.app.view.styles.DragAndDropStylesheet
import javafx.geometry.Pos
import javafx.scene.input.Dragboard
import javafx.scene.input.TransferMode
import javafx.scene.layout.Priority
import javafx.scene.text.TextAlignment
import tornadofx.View
import tornadofx.addClass
import tornadofx.addStylesheet
import tornadofx.gridpane
import tornadofx.hgrow
import tornadofx.removeClass
import tornadofx.style
import tornadofx.text
import tornadofx.textflow
import tornadofx.useMaxSize

class DragAndDropView : View() {

    private val printerController by inject<PrinterController>()

    override val root = gridpane {
        addStylesheet(DragAndDropStylesheet::class)

        addClass(DragAndDropStylesheet.dragAndDropPane)

        hgrow = Priority.ALWAYS

        useMaxSize = true
        alignment = Pos.CENTER

        textflow {
            text("Choose a file") {
                addClass(DragAndDropStylesheet.clickableTextButton)
            }

            text(", ")

            text("choose from catalog") {
                addClass(DragAndDropStylesheet.clickableTextButton)
            }

            text(" or drag it here")

            style {
                alignment = Pos.CENTER
                textAlignment = TextAlignment.CENTER
            }
        }

        setOnDragEntered {
            addClass(DragAndDropStylesheet.dragAndDropPaneIsDragover)
        }

        setOnDragExited {
            removeClass(DragAndDropStylesheet.dragAndDropPaneIsDragover)
        }

        setOnDragOver { ev ->
            if (ev.gestureSource != this && ev.dragboard.hasOnlyAllowedFiles()) {
                ev.acceptTransferModes(*TransferMode.COPY_OR_MOVE)
            }

            ev.consume()
        }

        setOnDragDropped { ev ->
           val file = ev.dragboard
               .getOnlyAllowedFiles()
               ?: throw IllegalStateException("File was accepted by setOnDragOver, but not passed to setOnDragDropped")

            printerController.setFileToUpload(file)
        }
    }

    private fun Dragboard.allowedFilesSequence() =
        files.asSequence()
            .filter { it.extension in listOf("g", "gcode", "gco", "ngc") }

    private fun Dragboard.hasOnlyAllowedFiles() =
        allowedFilesSequence().count() == 1

    private fun Dragboard.getOnlyAllowedFiles() =
        allowedFilesSequence().firstOrNull()
}