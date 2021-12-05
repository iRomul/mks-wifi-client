package io.github.iromul.mkstransfer.app.view

import io.github.iromul.mkstransfer.app.controller.PrinterController
import io.github.iromul.mkstransfer.app.view.upload.GCodeUploadView
import tornadofx.View
import tornadofx.borderpane
import tornadofx.listmenu
import tornadofx.useMaxHeight

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

                whenSelected {
                    center<GCodeUploadView>()
                }
            }

            item("Control") {
                whenSelected {
                    center<PrinterControlView>()
                }
            }

            item("Settings") {
                whenSelected {
                    center<PrinterSettingView>()
                }
            }
        }

        center<GCodeUploadView>()
    }
}