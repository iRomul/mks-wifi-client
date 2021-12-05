package io.github.iromul.mkstransfer.app.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.getValue
import tornadofx.setValue

class PrinterStatus(
    status: String
) {

    val statusProperty = SimpleStringProperty(status)
    var status by statusProperty
}