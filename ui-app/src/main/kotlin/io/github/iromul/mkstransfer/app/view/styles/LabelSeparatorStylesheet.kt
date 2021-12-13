package io.github.iromul.mkstransfer.app.view.styles

import io.github.iromul.commons.tornadofx.labeledSeparator
import javafx.geometry.VPos
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.px

class LabelSeparatorStylesheet : Stylesheet() {

    init {
        labeledSeparator {
            padding = box(5.px, 0.px, 10.px, 0.px)

            label {

            }

            separator {
                vAlignment = VPos.CENTER
                padding = box(0.px, 0.px, 0.px, 5.px)
            }
        }
    }
}