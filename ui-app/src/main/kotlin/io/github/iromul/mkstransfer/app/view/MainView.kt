package io.github.iromul.mkstransfer.app.view

import io.github.iromul.mkstransfer.app.controller.PrinterController
import io.github.iromul.mkstransfer.app.view.components.windowToolbar
import io.github.iromul.mkstransfer.app.view.control.PrinterControlView
import io.github.iromul.mkstransfer.app.view.settings.SettingView
import io.github.iromul.mkstransfer.app.view.upload.FileUploadMainView
import javafx.application.Platform
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import tornadofx.View
import tornadofx.addClass
import tornadofx.borderpane
import tornadofx.listmenu
import tornadofx.top
import tornadofx.useMaxHeight
import kotlin.system.exitProcess

class MainView : View(
    title = "MKS WiFi Transfer"
) {

    val printerController by inject<PrinterController>()

    override val root = borderpane {
        setPrefSize(500.0, 500.0)

        top {
            windowToolbar()
        }

        left = listmenu(theme = "solarized") {
            useMaxHeight = true

            item("GCode File") {
                activeItem = this

                graphic = ImageView().apply {
                    image = Image(resources["/icons/cloud upload@20px.png"])
                }

                whenSelected {
                    center<FileUploadMainView>()
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
                graphic = ImageView().apply {
                    image = Image(resources["/icons/help@20px.png"])
                }

                whenSelected {
                    center<AboutView>()
                }
            }

            item("Quit") {
                addClass("dangerous")

                graphic = ImageView().apply {
                    image = Image(resources["/icons/exit to app@20px.png"])
                }

                whenSelected {
                    Platform.exit()
                    exitProcess(0)
                }
            }
        }

        center<FileUploadMainView>()
    }
}