package io.github.iromul.commons.javafx.scene.control

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.AccessibleRole
import javafx.scene.control.Control
import javafx.scene.control.Label
import javafx.scene.control.Separator
import javafx.scene.control.Skin
import javafx.scene.control.SkinBase
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority

class LabeledSeparator(
    text: String = ""
) : Control() {

    init {
        accessibleRole = AccessibleRole.TITLED_PANE
        styleClass.add("labeled-separator")
    }

    val textProperty = SimpleStringProperty(this, "labeledSeparator", text)

    var text: String
        get() = textProperty.get()
        set(value) = textProperty.set(value)

    override fun createDefaultSkin(): Skin<*> {
        return LabeledSeparatorSkin(this)
    }

    private class LabeledSeparatorSkin(
        control: LabeledSeparator
    ) : SkinBase<LabeledSeparator>(control) {

        init {
            val text = Label(control.textProperty.get())
            text.textProperty().bind(control.textProperty)

            val separator = Separator(Orientation.HORIZONTAL)

            HBox.setHgrow(separator, Priority.ALWAYS)

            val hBox = HBox(text, separator)

            hBox.alignment = Pos.CENTER
            hBox.styleClass.setAll("label-container")

            children.add(hBox)
        }
    }
}