package io.github.iromul.mkstransfer.app.view.upload

import io.github.iromul.commons.javafx.materialdesign.icons.MaterialIcons
import io.github.iromul.commons.lang.userHome
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
import tornadofx.action
import tornadofx.addClass
import tornadofx.c
import tornadofx.chooseFile
import tornadofx.constraintsForRow
import tornadofx.gridpane
import tornadofx.gridpaneConstraints
import tornadofx.hgrow
import tornadofx.hyperlink
import tornadofx.px
import tornadofx.removeClass
import tornadofx.row
import tornadofx.style
import tornadofx.text
import tornadofx.textflow
import tornadofx.useMaxSize
import java.io.File

class FileSelectionView : View() {

    private val printerController by inject<PrinterController>()

    override val root = gridpane {
        addClass(MainStylesheet.dragAndDropPane)

        hgrow = Priority.ALWAYS

        useMaxSize = true
        alignment = Pos.CENTER

        constraintsForRow(0).percentHeight = 40.0
        constraintsForRow(1).percentHeight = 20.0
        constraintsForRow(2).percentHeight = 40.0

        row()

        row {
            textflow {
                hyperlink("Choose a file") {
                    addClass(MainStylesheet.dragAndDropText, MainStylesheet.dragAndDropClickableText)
                }.action {
                    val lastDir =
                        config.string("last_dir_path")?.let(::File)

                    val rootDir = if (lastDir != null && lastDir.exists() &&  lastDir.isDirectory && lastDir.canRead()) {
                        lastDir
                    } else {
                        File(userHome)
                    }

                    val selectedFile = chooseFile(
                        "Select G-code file",
                        arrayOf(AllowedGCCodeExtensions.fileChooserFilter),
                        rootDir
                    ).firstOrNull()

                    selectedFile?.let {
                        val selectedFileDir = selectedFile.parentFile

                        if (selectedFileDir.exists() && selectedFileDir.isDirectory && selectedFileDir.canRead()) {
                            config["last_dir_path"] = selectedFileDir.absolutePath
                            config.save()
                        }

                        printerController.setFileToUpload(it)
                    }
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
            text(MaterialIcons.fileUpload) {
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
            .filter { it.extension in AllowedGCCodeExtensions.allowedExtensions }

    private fun Dragboard.hasOnlyAllowedFiles() =
        allowedFilesSequence().count() == 1

    private fun Dragboard.getOnlyAllowedFiles() =
        allowedFilesSequence().firstOrNull()
}