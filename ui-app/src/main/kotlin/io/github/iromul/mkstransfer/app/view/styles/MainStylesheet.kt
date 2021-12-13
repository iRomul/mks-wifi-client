package io.github.iromul.mkstransfer.app.view.styles

import javafx.scene.Cursor
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.paint.Color
import javafx.scene.paint.Color.TRANSPARENT
import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.c
import tornadofx.cssclass
import tornadofx.loadFont
import tornadofx.multi
import tornadofx.px

class MainStylesheet : Stylesheet(
    LabelSeparatorStylesheet::class,
    WindowToolbarStylesheet::class
) {

    companion object {
        val dragAndDropText by cssclass()
        val dragAndDropClickableText by cssclass()
        val dragAndDropPane by cssclass()
        val dragAndDropPaneIsDragover by cssclass()
    }

    init {
        loadFont("/icons/MaterialIcons-Regular.ttf", 16.0)
        loadFont("/icons/MaterialIconsOutlined-Regular.otf", 16.0)

        star {
            baseColor = c("#3c3f41")
//            backgroundColor = multi(c("#3c3f41"))
            borderRadius = multi(box(0.px))
            borderStyle = multi(BorderStrokeStyle.NONE)
        }

        // .tab-pane
        //	.tab-content-area
        //	.tab-header-area
        //		.tab-header-background
        //		.headers-region
        //			.tab
        //				.tab-container
        //					.tab-label
        //						.text
        //					.focus-indicator

        tabPane {
            tabHeaderArea {
                tabHeaderBackground {
                    backgroundColor = multi(c("#3c3f41"))
                }

                tab {
                    backgroundColor = multi(Color.RED)

                    and(top) {
                        borderRadius = multi(box(0.px))
                        borderStyle = multi(BorderStrokeStyle.NONE)

                        and(selected) {
                            borderRadius = multi(box(0.px))
                            borderStyle = multi(BorderStrokeStyle.NONE)
                        }
                    }
                }
            }
        }

        dragAndDropText {
            fill = c("#A9B7C6")
        }

        dragAndDropClickableText {
            fontWeight = FontWeight.BOLD
            cursor = Cursor.HAND
            fill = c("#FFC66D")
        }

        dragAndDropPane {
            baseColor = c("#cfcfcf")
            fontSize = 20.px
            backgroundColor += c("#2b2b2b")
            borderStyle += BorderStrokeStyle.DASHED
            borderColor += box(c("#A9B7C6"))
            borderWidth += box(4.px)
            borderInsets += box(6.px)
            borderRadius += box(30.px)
            padding = box(10.px)
        }

        dragAndDropPaneIsDragover {
            backgroundColor = multi(Color.GREY)
        }

        // Solarized
        val listMenuBackgroundColor = c("#3c3f41")
        val listMenuHoverBackgroundColor = c("#353739")
        val listMenuActiveBackgroundColor = c("#2d2f30")

        s(".list-menu.solarized") {
            backgroundColor = multi(listMenuBackgroundColor)
            borderColor = multi(box(TRANSPARENT, c("#515151"), TRANSPARENT, TRANSPARENT))
        }

        s(".list-menu.solarized .list-item") {
            backgroundColor = multi(listMenuBackgroundColor)
        }

        s(".list-menu.solarized .list-item:hover") {
            backgroundColor = multi(listMenuHoverBackgroundColor)
        }

        s(".list-menu.solarized .list-item:hover.dangerous") {
            backgroundColor = multi(c("#e81123"))
        }

        s(".list-menu.solarized .list-item:active") {
            backgroundColor = multi(listMenuActiveBackgroundColor)
        }

        s(".list-menu.solarized .list-item *") {
            fill = c("#afb1b3")
        }

        s(".list-menu.solarized .list-item:active *") {
            fill = Color.WHITE
        }

        s(".list-menu.solarized .list-item:active:hover .graphic") {
            fill = Color.WHITE
        }
    }
}