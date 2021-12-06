package io.github.iromul.gcode.file.impl

import io.github.iromul.gcode.file.MutableGCodeFile
import io.github.iromul.gcode.file.line.GCodeLine

class BufferedGCodeFile(
    byteArray: ByteArray,
) : MutableGCodeFile {

    override val lines: MutableList<GCodeLine>

    init {
        lines = byteArray.inputStream().buffered().reader().use { reader ->
            reader.useLines { it.map(::GCodeLine).toMutableList() }
        }
    }

    override fun trimHead() {
        val exceptFirst = lines.dropWhile { it.isEmpty || it.isComment }

        lines.apply {
            clear()
            addAll(exceptFirst)
        }
    }

    override fun appendLine(line: GCodeLine, pos: Int) {
        lines.add(pos, line)
    }

    override fun appendLines(lines: Collection<GCodeLine>, pos: Int) {
        this.lines.addAll(pos, lines)
    }
}