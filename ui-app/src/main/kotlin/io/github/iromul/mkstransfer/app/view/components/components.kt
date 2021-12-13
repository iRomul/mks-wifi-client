package io.github.iromul.mkstransfer.app.view.components

import javafx.event.EventTarget
import tornadofx.opcr

fun EventTarget.windowToolbar(op: WindowToolbar.() -> Unit = {}): WindowToolbar {
    val windowToolbar = WindowToolbar()
    opcr(this, windowToolbar, op)
    return windowToolbar
}