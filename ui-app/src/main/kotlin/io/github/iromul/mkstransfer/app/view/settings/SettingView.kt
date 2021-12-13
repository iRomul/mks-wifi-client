package io.github.iromul.mkstransfer.app.view.settings

import io.github.iromul.commons.tornadofx.labeledseparator
import io.github.iromul.mkstransfer.app.model.settings.PreferencesModel
import io.github.iromul.mkstransfer.app.model.settings.printer.MksTftUploadSettingsModel
import io.github.iromul.mkstransfer.app.model.settings.printer.PrintHostUploadMode
import io.github.iromul.mkstransfer.app.model.settings.printer.PrinterSettingsModel
import tornadofx.View
import tornadofx.action
import tornadofx.button
import tornadofx.buttonbar
import tornadofx.combobox
import tornadofx.enableWhen
import tornadofx.field
import tornadofx.fieldset
import tornadofx.form
import tornadofx.required
import tornadofx.tab
import tornadofx.tabpane
import tornadofx.textfield
import tornadofx.toObservable
import tornadofx.vbox

class SettingView : View() {

    private val preferences by inject<PreferencesModel>()
    private val printerSettings by inject<PrinterSettingsModel>()
    private val mksTftUploadSettingsModel by inject<MksTftUploadSettingsModel>()

    override val root = tabpane {
        tab("Preferences") {
            isClosable = false

            vbox {
                form {
                    labeledseparator("Application preferences")

                    fieldset {
                        field("Catalog path") {
                            textfield(preferences.catalogPath)
                        }
                    }

                    buttonbar {
                        button("Save")
                    }
                }
            }
        }

        tab("Printer Settings") {
            isClosable = false

            vbox {
                form {
                    labeledseparator("Printer settings")

                    fieldset {
                        field("Printer name") {
                            textfield(printerSettings.printerName).required()
                        }

                        field("Socket address") {
                            textfield(printerSettings.socketAddress).required()
                        }
                    }
                }

                form {
                    labeledseparator("Print host upload")

                    fieldset {
                        field("Upload host mode") {
                            combobox<PrintHostUploadMode>(printerSettings.printHostUploadMode) {
                                items = PrintHostUploadMode.values().toList().toObservable()
                            }
                        }
                    }

                    val mksTftMode = printerSettings.printHostUploadMode.isEqualTo(PrintHostUploadMode.MKS_TFT)

                    fieldset {
                        enableWhen(mksTftMode)

                        field("MKS Upload address") {
                            textfield(mksTftUploadSettingsModel.mksUploadAddress).required()
                        }

                        field("G-code thumbnails") {
                            textfield(mksTftUploadSettingsModel.gcodeThumbnails)
                        }
                    }

                    buttonbar {
                        button("Save").action {
                            printerSettings.commit()

                            if (printerSettings.printHostUploadMode.value == PrintHostUploadMode.MKS_TFT) {
                                mksTftUploadSettingsModel.commit()
                            }
                        }
                    }
                }
            }
        }
    }
}