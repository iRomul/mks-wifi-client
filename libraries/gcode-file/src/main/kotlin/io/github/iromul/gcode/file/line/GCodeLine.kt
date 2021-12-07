package io.github.iromul.gcode.file.line

data class GCodeLine(
    val line: String,
) {

    val isComment: Boolean =
        line.trim().startsWith(";")

    val isBlank: Boolean =
        line.trim().isBlank()

    val isCommand: Boolean =
        line.trim().takeIf { it.isNotEmpty() }?.let { it[0].isLetter() } ?: false

    val command: String
        get() {
            require(isCommand) { "Line $line is not a command" }

            return line
        }

    val commentText: String
        get() {
            require(isComment) { "Line $line is not a comment" }

            return line.trimStart { it.isWhitespace() || it == ';' }
        }
}