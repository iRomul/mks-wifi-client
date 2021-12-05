package io.github.iromul.mkstransfer.client

import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.util.network.NetworkAddress
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.ByteWriteChannel
import io.ktor.utils.io.readUTF8Line
import io.ktor.utils.io.writeFully
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class MksSocketClient {

    private val addr = NetworkAddress("192.168.1.72", 8080)

    private lateinit var socket: Socket
    private lateinit var input: ByteReadChannel
    private lateinit var output: ByteWriteChannel

    fun testConnection() {
        runBlocking {
            socket = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().connect(addr)
            input = socket.openReadChannel()
            output = socket.openWriteChannel(autoFlush = true)

//            output.writeFully(formatGcode("M997"))
//            output.flush()

//            sendGcodeAndReadLines("M997", 2)
            sendGcodeAndReadLines("M20", 100)
//            sendGcodeAndReadLines("M27", 2)
//            sendGcodeAndReadLines("M31", 2)
//            sendGcodeAndReadLines("M78", 2)

            readLines(5)
        }
    }

    fun close() {
        socket.close()
    }

    private suspend fun sendGcode(gcode: String) {
        println("Sending '$gcode'...")
        output.writeFully(formatGcode(gcode))
    }

    private suspend fun readLines(lines: Int = 1) {
        repeat(lines) {
            println("> ${input.readUTF8Line()}")
        }
    }

    private suspend fun sendGcodeAndReadLines(gcode: String, lines: Int = 1) {
        sendGcode(gcode)

        repeat(lines) {
            println("> ${input.readUTF8Line()}")
        }
    }

    private fun formatGcode(gcode: String): ByteArray {
        return "$gcode\n\r".toByteArray()
    }
}