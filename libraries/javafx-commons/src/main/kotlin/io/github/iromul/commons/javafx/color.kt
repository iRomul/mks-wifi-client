package io.github.iromul.commons.javafx

import javafx.scene.paint.Color

fun Color.opacity(opacity: Double) =
    Color(red, green, blue, opacity)