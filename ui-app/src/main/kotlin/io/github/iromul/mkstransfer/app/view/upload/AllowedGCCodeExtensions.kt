package io.github.iromul.mkstransfer.app.view.upload

object AllowedGCCodeExtensions {

    val allowedExtensions = listOf("g", "gcode", "gco", "ngc")
    val allowedExtensionsMask = allowedExtensions.map { "*.$it" }
}