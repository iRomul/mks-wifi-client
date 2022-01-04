package io.github.iromul.mkstransfer.app.controller

import javafx.application.Platform
import tornadofx.Controller

class NativeWindowController : Controller() {

    fun close() {
        Platform.exit()
    }

    fun minimize() {
        primaryStage.isIconified = true
    }
}