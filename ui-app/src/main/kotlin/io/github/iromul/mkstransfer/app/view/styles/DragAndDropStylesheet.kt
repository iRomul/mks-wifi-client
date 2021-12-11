package io.github.iromul.mkstransfer.app.view.styles

import javafx.scene.Cursor
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.c
import tornadofx.cssclass
import tornadofx.px

class DragAndDropStylesheet : Stylesheet() {

    companion object {
        val clickableTextButton by cssclass()
        val dragAndDropPane by cssclass()
        val dragAndDropPaneIsDragover by cssclass()
    }

    init {
        clickableTextButton {
            fontWeight = FontWeight.BOLD
            cursor = Cursor.HAND
        }

        dragAndDropPane {
            baseColor = c("#cfcfcf")
            fontSize = 20.px
            backgroundColor += Color.WHITE
            borderStyle += BorderStrokeStyle.DASHED
            borderColor += box(c("#cfcfcf"))
            borderWidth += box(4.px)
            borderInsets += box(6.px)
            borderRadius += box(30.px)
            padding = box(10.px)
        }

        dragAndDropPaneIsDragover {
            backgroundColor += Color.GREY
        }
    }
}