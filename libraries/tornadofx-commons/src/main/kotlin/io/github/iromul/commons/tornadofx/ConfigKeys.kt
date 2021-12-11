package io.github.iromul.commons.tornadofx

import tornadofx.App
import tornadofx.FX
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

abstract class ConfigKeys(
    val rootKey: String = ""
) {

    private val app: App get() = FX.application as App
    private val config = app.config

    inline fun <reified T : Any> configKey(key: String): ConfigKey<T> =
        configKey(key, T::class)

    fun <T : Any> configKey(key: String, type: KClass<T>): ConfigKey<T> =
        DefaultConfigKey(key, type)

    private inner class DefaultConfigKey<T : Any>(
        override val key: String,
        override val type: KClass<T>,
        val defaultValue: T? = null
    ) : ConfigKey<T> {

        override operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
            @Suppress("UNCHECKED_CAST")
            return config.getByType(type, absoluteKey, defaultValue)
        }

        override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
            config.set(absoluteKey to value)
        }

        private val absoluteKey: String
            get() = if (rootKey.isBlank()) key else "$rootKey.$key"
    }

    fun <T : Any> ConfigKey<T>.withInitial(initialValue: T): ConfigKey<T> =
        DefaultConfigKey(key, type, initialValue)

    fun saveConfig() {
        config.save()
    }
}

