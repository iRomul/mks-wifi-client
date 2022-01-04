package io.github.iromul.mkstransfer.app.commons.koin

import org.koin.core.KoinApplication
import tornadofx.DIContainer
import kotlin.reflect.KClass

class KoinTornadoFxDiContainer(
    private val koinApplication: KoinApplication
) : DIContainer {

    override fun <T : Any> getInstance(type: KClass<T>): T {
        return koinApplication.koin.get(type)
    }
}