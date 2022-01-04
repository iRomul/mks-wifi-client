package io.github.iromul.mkstransfer.app.view.upload

import javafx.stage.FileChooser

object AllowedGCCodeExtensions {

    val allowedExtensions = listOf("g", "gcode", "gco", "ngc")

    val fileChooserFilter = FileChooser.ExtensionFilter(
        "G-code files",
        allowedExtensions.map { "*.$it" }
    )
}