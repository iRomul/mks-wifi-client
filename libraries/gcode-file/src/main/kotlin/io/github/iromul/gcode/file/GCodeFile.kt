package io.github.iromul.gcode.file

import io.github.iromul.gcode.file.line.GCodeLine

interface GCodeFile {

    val lines: List<GCodeLine>
}