package io.github.iromul.mkstransfer.app.view.components

import io.github.iromul.commons.lang.requireResource
import io.github.iromul.mkstransfer.app.view.styles.WindowToolbarStylesheet
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.ToolBar
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import javafx.scene.text.Text
import tornadofx.FX.Companion.primaryStage
import tornadofx.Stylesheet.Companion.title
import tornadofx.action
import tornadofx.addClass

class WindowToolbar : ToolBar() {

    init {
        val stage = primaryStage

        addClass(WindowToolbarStylesheet.windowToolbar)

        val iconImage = Image(requireResource("/icons/benchy-orange@64px.png").toExternalForm(), 16.0, 16.0, true, true)

        val icon = ImageView(iconImage)

        val text = Text().apply {
            addClass(title)

            textProperty().bind(stage.titleProperty())
        }

        val spacer = Pane().apply {
            HBox.setHgrow(this, Priority.ALWAYS)
        }

        icon.resize(16.0, 16.0)

        val minimizeBtn = Button("\ue931").apply {
            addClass(WindowToolbarStylesheet.actionButton)

            action {
                stage.isIconified = true
            }
        }

        val closeBtn = Button("\ue5cd").apply {
            addClass(WindowToolbarStylesheet.actionButton, WindowToolbarStylesheet.close)

            action {
                Platform.exit()
            }
        }

        items.addAll(icon, text, spacer, minimizeBtn, closeBtn)

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