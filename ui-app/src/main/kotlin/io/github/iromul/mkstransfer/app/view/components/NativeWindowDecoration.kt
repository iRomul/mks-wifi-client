package io.github.iromul.mkstransfer.app.view.components

import io.github.iromul.mkstransfer.app.controller.NativeWindowController
import io.github.iromul.mkstransfer.app.view.styles.native.win.Win10NativeStylesheet
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.input.MouseButton
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import tornadofx.FX.Companion.primaryStage
import tornadofx.Stylesheet
import tornadofx.action
import tornadofx.addChildIfPossible
import tornadofx.addClass

class NativeWindowDecoration : VBox() {

    private val controller = NativeWindowController()

    init {
        val stage = primaryStage

        addClass(Win10NativeStylesheet.nativeWindow)

        val minimizeButton = Button().apply {
            addClass(Win10NativeStylesheet.windowButton, Win10NativeStylesheet.button, Win10NativeStylesheet.minimizeButton)

            addChildIfPossible(StackPane(Region().apply { addClass(Win10NativeStylesheet.icon) }))

            action {
                controller.minimize()
            }
        }

        val closeButton = Button().apply {
            addClass(Win10NativeStylesheet.windowButton, Win10NativeStylesheet.button, Win10NativeStylesheet.closeButton)

            addChildIfPossible(StackPane(Region().apply { addClass(Win10NativeStylesheet.icon) }))

            action {
                controller.close()
            }
        }

        val windowTitle = Text().apply {
            addClass(Stylesheet.title)

            textProperty().bind(stage.titleProperty())
        }

        val spacer = Pane().apply {
            HBox.setHgrow(this, Priority.ALWAYS)
        }

        val windowButton = HBox(windowTitle, spacer, minimizeButton, closeButton).apply {
            addClass(Win10NativeStylesheet.windowControl)
        }

        addChildIfPossible(windowButton)

        val dragDelta = Delta(0.0, 0.0)

        onMousePressed = EventHandler {
            if (it.button == MouseButton.PRIMARY) {
                dragDelta.x = stage.x - it.screenX
                dragDelta.y = stage.y - it.screenY
            }
        }

        onMouseDragged = EventHandler {
            if (it.button == MouseButton.PRIMARY) {
                stage.x = it.screenX + dragDelta.x
                stage.y = it.screenY + dragDelta.y
            }
        }
    }

    private data class Delta(
        var x: Double,
        var y: Double
    )
}