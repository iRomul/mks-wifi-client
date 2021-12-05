package io.github.iromul.commons.kotlin.reflection

fun requireResource(name: String, receiver: () -> Unit) =
    requireNotNull(
        receiver.javaClass.getResource(name)
    ) { "Required resource $name is null" }
