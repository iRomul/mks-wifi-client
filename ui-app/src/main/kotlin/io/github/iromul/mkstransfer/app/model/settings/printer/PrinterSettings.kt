package io.github.iromul.mkstransfer.app.model.settings.printer

data class PrinterSettings(
    var printerName: String,
    var socketAddress: String?,
    var printHostUploadMode: PrintHostUploadMode
)