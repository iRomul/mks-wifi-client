package io.github.iromul.commons.tornadofx

import io.github.iromul.commons.javafx.scene.control.LabeledSeparator
import javafx.event.EventTarget
import tornadofx.opcr

fun EventTarget.labeledseparator(text: String, op: LabeledSeparator.() -> Unit = {}) =
    opcr(this, LabeledSeparator(text), op)