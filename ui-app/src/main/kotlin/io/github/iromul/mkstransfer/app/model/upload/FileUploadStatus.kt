package io.github.iromul.mkstransfer.app.model.upload

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.getValue
import tornadofx.setValue

class FileUploadStatus(
    status: UploadStatus,
    error: String?
) {

    val statusProperty = SimpleObjectProperty(status, "status", UploadStatus.IDLE)
    var status by statusProperty

    val errorProperty = SimpleStringProperty(error, "error")
    var error by errorProperty
}