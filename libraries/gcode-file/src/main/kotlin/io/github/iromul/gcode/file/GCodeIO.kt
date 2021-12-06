package io.github.iromul.gcode.file

import io.github.iromul.gcode.file.impl.BufferedGCodeFile
import io.github.iromul.gcode.file.line.GCodeLine
import java.io.Writer

object GCodeIO {

    fun buffered(byteArray: ByteArray): MutableGCodeFile =
        BufferedGCodeFile(byteArray)

    fun write(file: GCodeFile, writer: Writer) {
        writer.write(file.lines.joinToString(separator = "\r", transform = GCodeLine::line))
    }
}