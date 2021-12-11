package io.github.iromul.commons.kotlin.types

inline fun <reified T : Enum<T>> enumValueOf(name: String): T =
    enumValueOf(T::class.java, name)

fun <T : Enum<T>> enumValueOf(enumClass: Class<T>, name: String): T {
    return java.lang.Enum.valueOf(enumClass, name)
}