package io.github.iromul.gcode.file

import io.github.iromul.gcode.file.line.GCodeLine

interface MutableGCodeFile : GCodeFile {

    override val lines: MutableList<GCodeLine>

    /**
     * Trim comments and whitespaces from start of file until first command (see [GCodeLine.isCommand])
     */
    fun trimHead()

    /**
     * Appends line at the specific position of file
     */
    fun appendLine(line: GCodeLine, pos: Int = 0)

    /**
     * Appends multiple lines at the specific position of file
     */
    fun appendLines(lines: Collection<GCodeLine>, pos: Int = 0)
}