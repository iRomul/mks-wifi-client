package io.github.iromul.gcode.file.line

object GCodeLines {

    fun comment(text: String = "") =
        GCodeLine(";$text")

    fun command(cmd: String, vararg args: String) =
        GCodeLine(cmd + (args.takeIf { it.isNotEmpty() }?.joinToString(" ", prefix = " ") ?: ""))

    fun emptyLine() =
        GCodeLine("")
}