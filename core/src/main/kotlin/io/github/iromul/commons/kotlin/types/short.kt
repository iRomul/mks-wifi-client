package io.github.iromul.commons.kotlin.types

fun UShort.reverseByteOrder(): UShort = java.lang.Short.reverseBytes(toShort()).toUShort()