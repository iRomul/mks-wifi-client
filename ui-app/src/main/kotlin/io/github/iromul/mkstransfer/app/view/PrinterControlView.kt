package io.github.iromul.mkstransfer.app.view

import tornadofx.View
import tornadofx.borderpane
import tornadofx.bottom
import tornadofx.button
import tornadofx.hbox
import tornadofx.label
import tornadofx.top
import tornadofx.vbox

class PrinterControlView : View() {

    override val root = hbox {
        label("File")

        vbox {
            borderpane {
                top {
                    button("Up")
                }

                bottom {
                    button("Down")
                }
            }
        }
    }
}