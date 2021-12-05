package io.github.iromul.mkstransfer.app.view

import io.github.iromul.mkstransfer.app.controller.PrinterController
import tornadofx.View
import tornadofx.action
import tornadofx.button
import tornadofx.buttonbar
import tornadofx.field
import tornadofx.fieldset
import tornadofx.form
import tornadofx.required
import tornadofx.textfield

class PrinterSettingView : View() {

    private val printerController by inject<PrinterController>()
    private val printerSettings = printerController.printerSettings

    override val root = form {
        fieldset {
            field("IP Address") {
                textfield(printerSettings.ipAddress).required()
            }
        }

        buttonbar {
            button("Test connection").action {
                printerSettings.commit {
                    printerController.testConnection()
                }
            }
        }
    }
}