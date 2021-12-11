package io.github.iromul.mkstransfer.app

import io.github.iromul.commons.lang.userHome
import io.github.iromul.commons.nio.file.pathOf
import io.github.iromul.mkstransfer.app.service.AboutService
import io.github.iromul.mkstransfer.app.service.SendService
import io.github.iromul.mkstransfer.app.view.MainView
import javafx.scene.image.Image
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.fileProperties
import tornadofx.App
import tornadofx.DIContainer
import tornadofx.FX
import tornadofx.addStageIcon
import tornadofx.launch
import kotlin.reflect.KClass

val beans = module {
    single { SendService() }

    single { AboutService(getProperty("app.version"), "", "") }
}

class MksWifiUiApp : App(MainView::class) {

    init {
        val koinApp = startKoin {
            fileProperties()
            printLogger()
            modules(beans)
        }

        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>): T =
                koinApp.koin.get(type)
        }

        addStageIcon(Image(resources["/icons/benchy-orange@64px.png"]))
        addStageIcon(Image(resources["/icons/benchy-orange@128px.png"]))
        addStageIcon(Image(resources["/icons/benchy-orange@256px.png"]))
    }

    override val configBasePath = pathOf(userHome, ".mks-wifi-transfer")
}

fun main(args: Array<String>) {
    launch<MksWifiUiApp>(args)
}
