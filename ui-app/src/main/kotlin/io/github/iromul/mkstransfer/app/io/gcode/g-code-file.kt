package io.github.iromul.mkstransfer.app.io.gcode

import java.io.Reader
import java.io.Writer

interface GCodeFile {

    val lines: List<GCodeLine>
}

interface MutableGCodeFile : GCodeFile {

    override val lines: MutableList<GCodeLine>

    fun trimHead()
    fun appendLine(line: GCodeLine, pos: Int = 0)
}

class DefaultGCodeFile(
    override val lines: MutableList<GCodeLine>,
) : MutableGCodeFile {

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
}

object GCodeFileReader {

    fun load(file: ByteArray): DefaultGCodeFile {
        return load(file.inputStream().reader())
    }

    fun load(file: Reader): DefaultGCodeFile {
        val lines = file.useLines { it.map(::GCodeLine).toMutableList() }

        return DefaultGCodeFile(lines)
    }

    fun save(gCodeFile: GCodeFile, file: Writer) {
        file.write(gCodeFile.lines.joinToString(separator = "\r", transform = GCodeLine::stringify))
    }
}

data class GCodeLine(
    private val line: String,
) {

    val isComment: Boolean =
        line.trim().startsWith(";")

    val isEmpty: Boolean =
        line.trim().isEmpty()

    val isCommand: Boolean =
        line.trim().takeIf { it.isNotEmpty() }?.let { it[0].isLetter() } ?: false

    val commentText: String
        get() {
            require(isComment) { "Line $line is not a comment" }

            return line.trimStart { it.isWhitespace() || it == ';' }
        }

    fun stringify(): String {
        return line
    }
}

object GCodeLines {

    fun comment(text: String = "") =
        GCodeLine(";$text")

    fun command(cmd: String, vararg args: String) =
        GCodeLine(cmd + (args.takeIf { it.isNotEmpty() }?.joinToString(" ", prefix = " ") ?: ""))

    fun emptyLine() =
        GCodeLine("")
}