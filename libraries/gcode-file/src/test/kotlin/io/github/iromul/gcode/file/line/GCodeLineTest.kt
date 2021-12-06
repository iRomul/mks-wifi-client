package io.github.iromul.gcode.file.line

import io.kotest.assertions.withClue
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class GCodeLineTest {

    @Nested
    inner class Constructor {

        @Test
        internal fun `should be isComment for comment line`() {
            val line = GCodeLine(";Comment")

            withClue("should be isComment") {
                line.isComment.shouldBeTrue()
                line.isBlank.shouldBeFalse()
                line.isCommand.shouldBeFalse()
            }
        }

        @Test
        internal fun `should be isBlank for empty line`() {
            val line = GCodeLine("")

            withClue("should be isBlank") {
                line.isComment.shouldBeFalse()
                line.isBlank.shouldBeTrue()
                line.isCommand.shouldBeFalse()
            }
        }

        @Test
        internal fun `should be isCommand for comment line`() {
            val line = GCodeLine("G28")

            withClue("should be isCommand") {
                line.isComment.shouldBeFalse()
                line.isBlank.shouldBeFalse()
                line.isCommand.shouldBeTrue()
            }
        }
    }
}