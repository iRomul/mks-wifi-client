package io.github.iromul.mkstransfer.app

import javafx.event.Event
import javafx.event.EventType
import javafx.scene.paint.Color

class XEvent(
    eventType: EventType<out Event>
) : Event(eventType) {

    companion object {
        val ANY = EventType<XEvent>(Event.ANY, "ANY")

    }
}

fun Color.opacity(opacity: Double) =
    Color(red, green, blue, opacity)