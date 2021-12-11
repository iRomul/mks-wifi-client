package io.github.iromul.commons.tornadofx

import kotlin.reflect.KClass
import kotlin.reflect.KProperty

interface ConfigKey<T : Any> {

    val key: String
    val type: KClass<T>

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T?
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?)
}