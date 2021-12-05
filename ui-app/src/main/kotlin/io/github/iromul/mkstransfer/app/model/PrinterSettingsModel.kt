package io.github.iromul.mkstransfer.app.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel

class PrinterSettingsModel : ItemViewModel<PrinterSettings>() {

    companion object {
        const val IP_ADDRESS = "ip_address"
    }

    val ipAddress = bind { SimpleStringProperty(item?.ipAddress, "", app.config.string(IP_ADDRESS)) }

    override fun onCommit() {
        with(app.config) {
            set(IP_ADDRESS to ipAddress.value)
            save()
        }
    }
}