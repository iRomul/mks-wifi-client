package io.github.iromul.mkstransfer.app.view.upload

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.getValue
import tornadofx.setValue

object FileUploadMainViewData {

    val viewStateProperty = SimpleObjectProperty(FileUploadMainViewState.INITIAL)
    var viewState by viewStateProperty

    val navigationTitleProperty = SimpleStringProperty("")
    var navigationTitle by navigationTitleProperty
}