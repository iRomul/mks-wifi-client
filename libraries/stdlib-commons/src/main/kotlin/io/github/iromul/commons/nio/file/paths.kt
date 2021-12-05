package io.github.iromul.commons.nio.file

import java.nio.file.Path

fun pathOf(first: String, vararg more: String): Path = Path.of(first, *more)