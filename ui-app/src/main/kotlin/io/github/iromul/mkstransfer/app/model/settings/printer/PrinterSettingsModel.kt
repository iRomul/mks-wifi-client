package io.github.iromul.mkstransfer.app.model.settings.printer

import io.github.iromul.commons.javafx.beans.property.simpleObjectProperty
import io.github.iromul.commons.javafx.beans.property.simpleStringProperty
import io.github.iromul.commons.tornadofx.ConfigKeys
import javafx.beans.binding.BooleanBinding
import tornadofx.ItemViewModel

class PrinterSettingsModel : ItemViewModel<PrinterSettings>() {

    companion object : ConfigKeys("printer") {
        var printerNameKey by configKey<String>("printer_name")
            .withInitial("Common FFF")
        var socketAddressKey by configKey<String>("socket_address")
            .withInitial("192.168.0.1:8080")
        var printHostUploadModeKey by configKey<PrintHostUploadMode>("print_host_upload_mode")
            .withInitial(PrintHostUploadMode.NONE)
    }

    val printerName =
        bind { simpleStringProperty(item?.printerName, PrinterSettings::printerName, printerNameKey) }
    val socketAddress =
        bind { simpleStringProperty(item?.socketAddress, PrinterSettings::socketAddress, socketAddressKey) }
    val printHostUploadMode =
        bind { simpleObjectProperty(item?.printHostUploadMode, PrinterSettings::printHostUploadMode, printHostUploadModeKey) }

    val isMksTftHostUploadMode: BooleanBinding =
        printHostUploadMode.isEqualTo(PrintHostUploadMode.MKS_TFT)

    override fun onCommit() {
        printerNameKey = printerName.value
        socketAddressKey = socketAddress.value
        printHostUploadModeKey = printHostUploadMode.value

        saveConfig()
    }
}