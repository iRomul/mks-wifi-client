package io.github.iromul.mkstransfer.app

import io.github.iromul.mkstransfer.app.service.SendService
import io.github.iromul.mkstransfer.app.view.MainView
import javafx.scene.image.Image
import org.koin.core.context.startKoin
import org.koin.dsl.module
import tornadofx.App
import tornadofx.DIContainer
import tornadofx.FX
import tornadofx.addStageIcon
import tornadofx.launch
import java.nio.file.Path
import kotlin.reflect.KClass

val beans = module {
    single { SendService() }
}

class HelloFX : App(MainView::class) {

    init {
        val koinApp = startKoin {
            printLogger()
            modules(beans)
        }

        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>): T =
                koinApp.koin.get(type)
        }

        addStageIcon(Image(resources["/icons/benchy@64px.png"]))
        addStageIcon(Image(resources["/icons/benchy@128px.png"]))
        addStageIcon(Image(resources["/icons/benchy@256px.png"]))
    }

    override val configBasePath = pathOf(userHome, ".mks-wifi-transfer")
}

fun main(args: Array<String>) {
    launch<HelloFX>(args)
}

fun pathOf(first: String, vararg more: String): Path = Path.of(first, *more)

val userHome: String
    get() = System.getProperty("user.home")

