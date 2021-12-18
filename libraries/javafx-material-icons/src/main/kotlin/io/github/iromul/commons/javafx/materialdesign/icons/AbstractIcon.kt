package io.github.iromul.commons.javafx.materialdesign.icons

import javafx.scene.text.Font

abstract class AbstractIcon {

    abstract val font: Font
    abstract val className: String

    protected fun c(code: String) : String {
        return code
    }
}