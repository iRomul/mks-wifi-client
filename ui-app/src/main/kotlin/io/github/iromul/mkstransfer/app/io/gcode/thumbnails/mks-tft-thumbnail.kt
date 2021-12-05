package io.github.iromul.mkstransfer.app.io.gcode.thumbnails

import io.github.iromul.commons.kotlin.types.reverseBytes
import io.github.iromul.mkstransfer.app.io.gcode.GCodeLines
import io.github.iromul.mkstransfer.app.io.gcode.MutableGCodeFile
import javafx.scene.image.Image
import javafx.scene.paint.Color
import kotlin.math.roundToInt

class MksTftThumbnailWriter(
    private val gCodeFile: MutableGCodeFile,
    private val sImageSize: Size,
    private val gImageSize: Size,
) {

    fun writeAllThumbnails(thumbnails: Iterable<Image>) {
        val sImage = thumbnails.firstOrNull { it.intSize() == sImageSize }
        val gImage = thumbnails.firstOrNull { it.intSize() == gImageSize }

        val images = listOf("simage" to sImage, "gimage" to gImage)

        var pos = 0

        images.forEach { thumbnail ->
            val imageType = thumbnail.first
            val image = thumbnail.second

            image?.let {
                val reader = image.pixelReader

                (0 until image.height.toInt()).forEach { y ->
                    val lineStringBuilder = StringBuilder()

                    (0 until image.width.toInt()).forEach { x ->
                        val pixel = reader.getColor(x, y)
                        val uint16pixel = pixel.to16BitPixel().reverseBytes().toShort()

                        lineStringBuilder.append(String.format("%04x", uint16pixel))
                    }

                    val lineString = lineStringBuilder.toString()

                    if (y == 0) {
                        gCodeFile.appendLine(GCodeLines.comment(";$imageType:$lineString"), pos)
                    } else {
                        gCodeFile.appendLine(GCodeLines.command("M10086", ";$lineString"), pos)
                    }

                    ++pos
                }
            }

            gCodeFile.appendLine(GCodeLines.command("M10086", ";"), pos++)
            gCodeFile.appendLine(GCodeLines.emptyLine(), pos)
        }
    }

    private fun Image.intSize() =
        Size(width.toInt(), height.toInt())

    private fun Color.to16BitPixel(): UShort {
        val redInt = (red * 255.0).roundToInt()
        val greenInt = (green * 255.0).roundToInt()
        val blueInt = (blue * 255.0).roundToInt()

        return ((redInt shr 3 shl 11) or (greenInt shr 2 shl 5) or (blueInt shr 3)).toUShort()
    }
}
