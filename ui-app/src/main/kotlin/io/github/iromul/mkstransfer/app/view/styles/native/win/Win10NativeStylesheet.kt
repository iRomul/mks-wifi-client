package io.github.iromul.mkstransfer.app.view.styles.native.win

import javafx.scene.Cursor
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import tornadofx.MultiValue
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.c
import tornadofx.cssclass
import tornadofx.cssproperty
import tornadofx.derive
import tornadofx.multi
import tornadofx.px

class Win10NativeStylesheet : Stylesheet()  {

    companion object {
        val nativeWindow by cssclass()
        val windowControl by cssclass()
        val windowButton by cssclass()
        val button by cssclass()
        val icon by cssclass()
        val minimizeButton by cssclass()
        val maximizeButton by cssclass()
        val restoreButton by cssclass()
        val closeButton by cssclass()

        val iconPaint by cssproperty<MultiValue<Paint>>()
    }

    init {
        nativeWindow {
            padding = box(0.px)

            title {
                fill = c("#afb1b3")
            }

            backgroundColor = multi(c("#3c3f41"))

            windowControl {
                borderColor = multi(box(Color.TRANSPARENT, Color.TRANSPARENT, c("#515151"), Color.TRANSPARENT))
            }
        }

        windowButton and button {
            iconPaint.value  = multi(c("#A9A9A9"))

            cursor = Cursor.DEFAULT
            backgroundColor = multi(Color.TRANSPARENT)
            minHeight = 30.px
            maxHeight = 30.px
            minWidth = 45.px
            maxWidth = 45.px
            startMargin = 0.px
            endMargin = 0.px
            padding = box(0.px)
            backgroundRadius = multi(box(0.px))

            icon {
                backgroundColor = multi(c("#A9A9A9"))
                maxWidth = 10.px
                maxHeight = 10.px
            }
        }

        windowButton and (button.and(hover)) {
            backgroundColor = multi(c("#00000011"))

            icon {
                backgroundColor = multi(Color.WHITE)
            }
        }

        windowButton and (button.and(pressed)) {
            backgroundColor = multi(c("#00000022"))
        }

        button {
            and(minimizeButton) {
                icon {
                    shape = "M 0 0 L 1 0 L 1 1 L 0 1 Z"
                    maxHeight = 1.px
                }
            }

            and(maximizeButton) {
                icon {
                    shape = "M 0 0 L 60 0 L 60 60 L 0 60 L 5 55 L 55 55 L 55 5 L 5 5 L 5 55 L 0 60 Z"
                }
            }

            and(restoreButton) {
                icon {
                    shape = "M 0 60 L 0 12 L 12 12 L 12 0 L 60 0 L 60 48 L 48 48 L 48 42 L 54 42 L 54 6 L 18 6 L 18 12 L 48 12 L 48 60 L 0 60 L 6 54 L 42 54 L 42 18 L 6 18 L 6 54 Z"
                }
            }

            and(closeButton) {
                minWidth = 45.px
                maxWidth = 45.px

                icon {
                    backgroundColor = multi(c("#A9A9A9"))
                    shape = "M 0 3 L 3 0 L 30 27 L 57 0 L 60 3 L 33 30 L 60 57 L 57 60 L 30 33 L 3 60 L 0 57 L 27 30 Z"
                }

                and(hover) {
                    backgroundColor = multi(c("#E81123"))
                }

                and(pressed) {
                    backgroundColor = multi(c("#E81123").derive(0.7))

                    icon {
                        backgroundColor = multi(Color.WHITE)
                    }
                }
            }
        }
    }
}