package io.github.iromul.mkstransfer.app.view

import io.github.iromul.mkstransfer.app.service.AboutService
import tornadofx.View
import tornadofx.imageview
import tornadofx.text
import tornadofx.vbox

class AboutView : View() {

    private val aboutService by di<AboutService>()

    override val root = vbox {
        imageview("/icons/benchy-orange@256px.png")

        text("Version: ${aboutService.version}")
    }
}