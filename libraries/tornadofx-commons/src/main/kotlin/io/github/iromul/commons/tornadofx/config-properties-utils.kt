package io.github.iromul.commons.tornadofx

import io.github.iromul.commons.kotlin.types.enumValueOf
import tornadofx.ConfigProperties
import kotlin.reflect.KClass

fun <T : Any> ConfigProperties.getByType(type: KClass<T>, key: String, defaultValue: T? = null): T? {
    @Suppress("UNCHECKED_CAST")
    return when {
        type == String::class -> defaultValue
            ?.let { string(key, it as String) }
            ?: string(key)
        type == Boolean::class -> defaultValue
            ?.let { boolean(key, it as Boolean) }
            ?: boolean(key)
        type == Double::class -> defaultValue
            ?.let { double(key, it as Double) }
            ?: double(key)
        type == Int::class -> defaultValue
            ?.let { int(key, it as Int) }
            ?: int(key)
        type.java.isEnum -> defaultValue
            ?.let { enum(type as KClass<Enum<*>>, key, it as Enum<*>) }
            ?: enum(type as KClass<Enum<*>>, key)
        else -> error("Unsupported type for config mapping: ${type.java}")
    } as T?
}

fun <E : Enum<E>> ConfigProperties.enum(type: KClass<E>, key: String): E? {
    return string(key)?.let { enumValueOf(type.java, it) }
}

fun <E : Enum<E>> ConfigProperties.enum(type: KClass<E>, key: String, defaultValue: E): E {
    return enumValueOf(type.java, string(key, defaultValue.name))
}