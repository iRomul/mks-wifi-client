package io.github.iromul.mkstransfer.app.view

import io.github.iromul.mkstransfer.app.controller.PrinterController
import io.github.iromul.mkstransfer.app.view.upload.GCodeUploadView
import javafx.application.Platform
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import tornadofx.View
import tornadofx.borderpane
import tornadofx.listmenu
import tornadofx.useMaxHeight
import kotlin.system.exitProcess

class MainView : View(
    title = "MKS WiFi Transfer"
) {

    val printerController by inject<PrinterController>()

    override val root = borderpane {
        setPrefSize(500.0, 500.0)

        left = listmenu(theme = "blue") {
            useMaxHeight = true

            item("GCode File") {
                activeItem = this

                graphic = ImageView().apply {
                    image = Image(resources["/icons/cloud upload@20px.png"])
                }

                whenSelected {
                    center<GCodeUploadView>()
                }
            }

            item("Control") {
                graphic = ImageView().apply {
                    image = Image(resources["/icons/gamepad@20px.png"])
                }

                whenSelected {
                    center<PrinterControlView>()
                }
            }

            item("Settings") {
                graphic = ImageView().apply {
                    image = Image(resources["/icons/settings@20px.png"])
                }

                whenSelected {
                    center<SettingView>()
                }
            }

            item("About") {
                whenSelected {
                    center<AboutView>()
                }
            }

            item("Quit") {
                graphic = ImageView().apply {
                    image = Image(resources["/icons/exit to app@20px.png"])
                }

                whenSelected {
                    Platform.exit()
                    exitProcess(0)
                }
            }
        }

        center<GCodeUploadView>()
    }
}