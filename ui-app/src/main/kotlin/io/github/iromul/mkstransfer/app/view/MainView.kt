package io.github.iromul.mkstransfer.app.view

import io.github.iromul.commons.javafx.materialdesign.icons.MaterialIcons
import io.github.iromul.mkstransfer.app.controller.PrinterController
import io.github.iromul.mkstransfer.app.view.components.nativeWindowDecoration
import io.github.iromul.mkstransfer.app.view.settings.SettingView
import io.github.iromul.mkstransfer.app.view.upload.FileUploadMainView
import javafx.application.Platform
import javafx.scene.paint.Color
import javafx.scene.text.Text
import tornadofx.View
import tornadofx.addClass
import tornadofx.borderpane
import tornadofx.listmenu
import tornadofx.px
import tornadofx.style
import tornadofx.useMaxHeight

class MainView : View(
    title = "MKS WiFi Transfer"
) {

    val printerController by inject<PrinterController>()

    override val root = nativeWindowDecoration {
        borderpane {
            setPrefSize(500.0, 500.0)

            left = listmenu(theme = "solarized") {
                useMaxHeight = true

                item("G-code File") {
                    activeItem = this

                    graphic = Text(MaterialIcons.cloudUpload).apply {
                        addClass(MaterialIcons.className)
                        style {
                            fill = Color.web("#afb1b3")
                            fontSize = 20.px
                        }
                    }

                    whenSelected {
                        center<FileUploadMainView>()
                    }
                }

                item("Settings") {
                    graphic = Text(MaterialIcons.settings).apply {
                        addClass(MaterialIcons.className)
                        style {
                            fill = Color.web("#afb1b3")
                            fontSize = 20.px
                        }
                    }

                    whenSelected {
                        center<SettingView>()
                    }
                }

                item("About") {
                    graphic = Text(MaterialIcons.help).apply {
                        addClass(MaterialIcons.className)
                        style {
                            fill = Color.web("#afb1b3")
                            fontSize = 20.px
                        }
                    }

                    whenSelected {
                        center<AboutView>()
                    }
                }

                item("Quit") {
                    addClass("dangerous")

                    graphic = Text(MaterialIcons.exitToApp).apply {
                        addClass(MaterialIcons.className)
                        style {
                            fill = Color.web("#afb1b3")
                            fontSize = 20.px
                        }
                    }

                    whenSelected {
                        Platform.exit()
                    }
                }
            }

            center<FileUploadMainView>()
        }
    }
}