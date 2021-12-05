package io.github.iromul.commons.kotlin.types

fun UShort.reverseBytes(): UShort = java.lang.Short.reverseBytes(toShort()).toUShort()