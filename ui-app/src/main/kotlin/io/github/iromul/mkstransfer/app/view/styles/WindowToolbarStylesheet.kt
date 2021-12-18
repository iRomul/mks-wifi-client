package io.github.iromul.mkstransfer.app.view.styles

import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.c
import tornadofx.cssclass
import tornadofx.multi
import tornadofx.px

class WindowToolbarStylesheet : Stylesheet() {

    companion object {
        val windowToolbar by cssclass()
        val actionButton by cssclass()
        val close by cssclass()
    }

    init {
        windowToolbar {
            backgroundColor = multi(c("#3c3f41"))
            borderColor = multi(box(Color.TRANSPARENT, Color.TRANSPARENT, c("#515151"), Color.TRANSPARENT))

            title {
                fill = c("#afb1b3")
                fontWeight = FontWeight.BOLD
            }

            actionButton {
                backgroundColor = multi(Color.TRANSPARENT)
                borderRadius = multi(box(0.px))

                text {
                    fill = c("#afb1b3")
                    fontFamily = "Material Icons Outlined"
                }

                and(hover) {
                    backgroundColor = multi(c("#4f5254"))
                }

                and(close) {
                    and(hover) {
                        backgroundColor = multi(c("#e81123"))

                        text {
                            fill = Color.WHITE
                        }
                    }
                }
            }
        }
    }
}