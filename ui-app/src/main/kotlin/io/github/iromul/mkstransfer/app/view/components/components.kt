package io.github.iromul.mkstransfer.app.view.components

import javafx.event.EventTarget
import tornadofx.opcr

fun EventTarget.nativeWindowDecoration(op: NativeWindowDecoration.() -> Unit = {}): NativeWindowDecoration {
    val windowToolbar = NativeWindowDecoration()
    opcr(this, windowToolbar, op)
    return windowToolbar
}