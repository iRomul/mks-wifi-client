package io.github.iromul.gcode.file.impl

import io.github.iromul.gcode.file.line.GCodeLine
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class BufferedGCodeFileTest {

    @Nested
    inner class Constructor {

        @Test
        internal fun `line number should be 0 for empty file`() {
            val byteArray = ByteArray(0)

            val file = BufferedGCodeFile(byteArray)

            file.lines.shouldBeEmpty()
        }

        @Test
        internal fun `line number should be 1 for one-line file`() {
            val byteArray = "G28".toByteArray()

            val file = BufferedGCodeFile(byteArray)

            file.lines.shouldHaveSize(1)
        }

        @Test
        internal fun `line number should be 2 for two-line file`() {
            val byteArray = "G28\nG105".toByteArray()

            val file = BufferedGCodeFile(byteArray)

            file.lines.shouldHaveSize(2)
        }
    }

    @Nested
    inner class TrimHead {

        @Test
        internal fun `should not fail for empty file`() {
            val file = file("")

            file.trimHead()

            file.lines.shouldHaveSize(0)
        }

        @Test
        internal fun `should return same file if no comments or white lines are present in file`() {
            val file = file("G28\nG105")

            file.trimHead()

            file.lines shouldContainExactly listOf(GCodeLine("G28"), GCodeLine("G105"))
        }

        @Test
        internal fun `should return trimmed file if comment is present in file`() {
            val file = file(";Comment\nG28")

            file.trimHead()

            file.lines shouldContainExactly listOf(GCodeLine("G28"))
        }

        @Test
        internal fun `should return trimmed file if blank line is present in file`() {
            val file = file("\nG28")

            file.trimHead()

            file.lines shouldContainExactly listOf(GCodeLine("G28"))
        }

        @Test
        internal fun `should return trimmed file if blank line and comment are present in file`() {
            val file = file("\n;Comment\nG28")

            file.trimHead()

            file.lines shouldContainExactly listOf(GCodeLine("G28"))
        }

        @Test
        internal fun `should not trim lines after first command`() {
            val file = file("\n;Comment\nG28\n;Comment_2")

            file.trimHead()

            file.lines shouldContainExactly listOf(GCodeLine("G28"), GCodeLine(";Comment_2"))
        }
    }

    private fun file(content: String) = BufferedGCodeFile(content.toByteArray())
}