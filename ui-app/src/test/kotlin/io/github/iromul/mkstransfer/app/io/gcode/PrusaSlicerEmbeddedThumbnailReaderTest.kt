package io.github.iromul.mkstransfer.app.io.gcode

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEmpty
import io.github.iromul.mkstransfer.app.io.gcode.thumbnails.Base64EncodedThumbnail
import io.github.iromul.mkstransfer.app.io.gcode.thumbnails.PrusaSlicerEmbeddedThumbnailReader
import org.junit.jupiter.api.Test

internal class PrusaSlicerEmbeddedThumbnailReaderTest {

    @Test
    internal fun `should return empty list if no embedded thumbnails are present in gcode file`() {
        val gCodeFile = object : GCodeFile {
            override val lines = emptyList<GCodeLine>()
        }

        val reader = PrusaSlicerEmbeddedThumbnailReader(gCodeFile)

        val thumbnails = reader.getAllEncodedThumbnails()

        assertThat(thumbnails).isEmpty()
    }

    @Test
    internal fun `should return one thumbnail if one thumbnail is present in gcode file`() {
        val gCodeFile = object : GCodeFile {
            override val lines = listOf(
                GCodeLine(";"),
                GCodeLine("; thumbnail begin 200x50 1111"),
                GCodeLine("; ABC"),
                GCodeLine("; thumbnail end")
            )
        }

        val reader = PrusaSlicerEmbeddedThumbnailReader(gCodeFile)

        val thumbnails = reader.getAllEncodedThumbnails()

        assertThat(thumbnails).containsExactly(
            Base64EncodedThumbnail(200, 50, "ABC")
        )
    }

    @Test
    internal fun `should return two thumbnails if two thumbnail are present in gcode file`() {
        val gCodeFile = object : GCodeFile {
            override val lines = listOf(
                GCodeLine(";"),
                GCodeLine("; thumbnail begin 200x50 1111"),
                GCodeLine("; ABC"),
                GCodeLine("; thumbnail end"),
                GCodeLine(";"),
                GCodeLine(";"),
                GCodeLine("; thumbnail begin 400x200 2222"),
                GCodeLine("; XYZ"),
                GCodeLine("; thumbnail end")
            )
        }

        val reader = PrusaSlicerEmbeddedThumbnailReader(gCodeFile)

        val thumbnails = reader.getAllEncodedThumbnails()

        assertThat(thumbnails).containsExactly(
            Base64EncodedThumbnail(200, 50, "ABC"),
            Base64EncodedThumbnail(400, 200, "XYZ")
        )
    }
}