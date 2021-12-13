package io.github.iromul.mkstransfer.app.model.upload

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.image.Image
import tornadofx.getValue
import tornadofx.integerBinding
import tornadofx.setValue

class SelectedFile(
    fileData: ByteArray? = null,
    modifiedFileData: ByteArray? = null,
    fileName: String? = null,
    fileThumbnail: Image? = null,
    hasFile: Boolean = false,
) {

    val hasFileProperty = SimpleBooleanProperty(hasFile)
    var hasFile by hasFileProperty

    val fileDataProperty = SimpleObjectProperty(fileData)
    var fileData by fileDataProperty

    val processedFileDataProperty = SimpleObjectProperty(modifiedFileData)
    var precessedFileData by processedFileDataProperty

    val fileNameProperty = SimpleStringProperty(fileName)
    var fileName by fileNameProperty

    val fileThumbnailProperty = SimpleObjectProperty(fileThumbnail)
    var fileThumbnail by fileThumbnailProperty

    val fileSizeProperty = fileDataProperty.integerBinding { it?.size ?: 0 }
    val fileSize by fileSizeProperty

    val readyToUpload: Boolean
        get() = hasFile && !fileName.isNullOrEmpty()

    val actualFileToUpload: ByteArray
        get() = precessedFileData ?: requireNotNull(fileData)

    fun clearFile() {
        hasFile = false
        fileData = null
        precessedFileData = null
        fileName = null
        fileThumbnail = null
    }
}