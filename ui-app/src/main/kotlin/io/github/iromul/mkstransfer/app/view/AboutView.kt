package io.github.iromul.mkstransfer.app.view

import io.github.iromul.mkstransfer.app.service.AboutService
import tornadofx.View
import tornadofx.field
import tornadofx.fieldset
import tornadofx.form
import tornadofx.imageview
import tornadofx.text
import tornadofx.vbox

class AboutView : View() {

    private val aboutService by di<AboutService>()

    override val root = vbox {
        form {
            imageview("/icons/benchy-orange@128px.png")

            fieldset {
                field("Version") {
                    text(aboutService.version)
                }

                field("Author") {
                    vbox {
                        text(aboutService.authorName)
                        text(aboutService.authorEmail)
                    }
                }

                field("License") {
                    text(aboutService.licenseName)
                }
            }
        }
    }
}