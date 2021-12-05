package io.github.iromul.commons.lang

fun getResource(name: String, receiver: () -> Unit = {}) =
    receiver.javaClass.getResource(name)

fun requireResource(name: String, receiver: () -> Unit = {}) =
    requireNotNull(
        getResource(name, receiver)
    ) { "Required resource $name is null" }
