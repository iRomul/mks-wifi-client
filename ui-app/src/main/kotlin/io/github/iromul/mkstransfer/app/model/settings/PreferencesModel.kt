package io.github.iromul.mkstransfer.app.model.settings

import io.github.iromul.commons.javafx.beans.property.simpleStringProperty
import io.github.iromul.commons.tornadofx.ConfigKeys
import tornadofx.ItemViewModel

class PreferencesModel : ItemViewModel<Preferences>() {

    companion object : ConfigKeys("preferences") {
        var catalogPathKey by configKey<String>("catalog_path")
    }

    val catalogPath =
        bind { simpleStringProperty(item?.catalogPath, Preferences::catalogPath, catalogPathKey) }

    override fun onCommit() {
        catalogPathKey = catalogPath.value

        saveConfig()
    }
}