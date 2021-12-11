package io.github.iromul.mkstransfer.app.model.settings.printer

import io.github.iromul.commons.javafx.beans.property.simpleStringProperty
import io.github.iromul.commons.tornadofx.ConfigKeys
import tornadofx.ItemViewModel

class MksTftUploadSettingsModel : ItemViewModel<MksTftUploadSettings>() {

    companion object : ConfigKeys("mks-tft") {
        var mksUploadAddressKey by configKey<String>("mks_upload_address")
            .withInitial("192.168.0.1:80")
        var gcodeThumbnailsKey by configKey<String>("gcode_thumbnails")
    }

    var mksUploadAddress =
        bind { simpleStringProperty(item?.mksUploadAddress, MksTftUploadSettings::mksUploadAddress, mksUploadAddressKey) }
    var gcodeThumbnails =
        bind { simpleStringProperty(item?.gcodeThumbnails, MksTftUploadSettings::gcodeThumbnails, gcodeThumbnailsKey) }

    override fun onCommit() {
        mksUploadAddressKey = mksUploadAddress.value
        gcodeThumbnailsKey = gcodeThumbnails.value

        saveConfig()
    }
}