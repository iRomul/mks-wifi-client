package io.github.iromul.mkstransfer.cli

import com.github.ajalt.clikt.core.CliktCommand
import io.github.iromul.mkstransfer.client.MksSocketClient


//class MksTransferCommand : NoRunCliktCommand(
//    name = "mks-transfer",
//    printHelpOnEmptyArgs = true
//)

class MksTransferCommand : CliktCommand(
    name = "mks-transfer"
) {

    override fun run() {
        val client = MksSocketClient()

        client.testConnection()
        client.close()
    }
}

fun main(args: Array<String>): Unit = MksTransferCommand()
    .main(args)
