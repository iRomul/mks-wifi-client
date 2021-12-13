package io.github.iromul.mkstransfer.app.view.upload

import io.github.iromul.mkstransfer.app.controller.PrinterController
import io.github.iromul.mkstransfer.app.view.styles.MainStylesheet
import javafx.geometry.HPos
import javafx.geometry.Pos
import javafx.geometry.VPos
import javafx.scene.input.Dragboard
import javafx.scene.input.TransferMode
import javafx.scene.layout.Priority
import javafx.scene.text.TextAlignment
import tornadofx.View
import tornadofx.addClass
import tornadofx.c
import tornadofx.constraintsForRow
import tornadofx.gridpane
import tornadofx.gridpaneConstraints
import tornadofx.hgrow
import tornadofx.px
import tornadofx.removeClass
import tornadofx.row
import tornadofx.style
import tornadofx.text
import tornadofx.textflow
import tornadofx.useMaxSize

class DragAndDropView : View() {

    private val printerController by inject<PrinterController>()

    override val root = gridpane {
        addClass(MainStylesheet.dragAndDropPane)

        hgrow = Priority.ALWAYS

        useMaxSize = true
        alignment = Pos.CENTER

//        (0..2).forEach {
//            constraintsForRow(it).apply {
//                percentHeight = 100.0 / 3
//            }
//        }

        constraintsForRow(0).percentHeight = 40.0
        constraintsForRow(1).percentHeight = 20.0
        constraintsForRow(2).percentHeight = 40.0

        row()

        row {
            textflow {
                text("Choose a file") {
                    addClass(MainStylesheet.dragAndDropText, MainStylesheet.dragAndDropClickableText)
                }

                text(", ") {
                    addClass(MainStylesheet.dragAndDropText)
                }

                text("choose from catalog") {
                    addClass(MainStylesheet.dragAndDropText, MainStylesheet.dragAndDropClickableText)
                }

                text(" or drag it here") {
                    addClass(MainStylesheet.dragAndDropText)
                }

                style(append = true) {
                    textAlignment = TextAlignment.CENTER
                }

                gridpaneConstraints {
                    vAlignment = VPos.CENTER
                    fillHeight = true
                }
            }
        }

        row {
            text("\ue2c6") {
                style {
                    fontFamily = "Material Icons Outlined"
                    fill = c("#A9B7C6")
                    fontSize = 72.px
                }

                gridpaneConstraints {
                    vAlignment = VPos.TOP
                    hAlignment = HPos.CENTER
                }

                alignment = Pos.TOP_CENTER
            }
        }

        setOnDragEntered {
            addClass(MainStylesheet.dragAndDropPaneIsDragover)
        }

        setOnDragExited {
            removeClass(MainStylesheet.dragAndDropPaneIsDragover)
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