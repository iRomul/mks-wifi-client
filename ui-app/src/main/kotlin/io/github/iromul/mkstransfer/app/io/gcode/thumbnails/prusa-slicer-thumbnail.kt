package io.github.iromul.mkstransfer.app.io.gcode.thumbnails

import io.github.iromul.mkstransfer.app.io.gcode.GCodeFile
import io.github.iromul.mkstransfer.app.io.gcode.GCodeLine
import javafx.scene.image.Image
import java.util.Base64

class PrusaSlicerEmbeddedThumbnailReader(
    private val gCodeFile: GCodeFile,
) {

    fun getAllThumbnails(): List<Image> {
        val base64decoder = Base64.getDecoder()

        return getAllEncodedThumbnails()
            .map {
                val decodedPngImage = base64decoder.decode(it.base64data)

                Image(decodedPngImage.inputStream())
            }
            .toList()
    }

    fun getAllEncodedThumbnails(): List<Base64EncodedThumbnail> {
        return gCodeFile.lines.asSequence()
            .mapIndexed(PrusaSlicerEmbeddedThumbnailReader::NumberedGCodeLine)
            .filter { it.line.isComment }
            .filter { it.line.commentText.contains("thumbnail begin") }
            .map {
                scanEncodedThumbnailData(it.lineNumber)
            }
            .toList()
    }

    private fun scanEncodedThumbnailData(skipNumber: Int): Base64EncodedThumbnail {
        var width = -1
        var height = -1

        val data = gCodeFile.lines.asSequence()
            .drop(skipNumber)
            .filter(GCodeLine::isComment)
            .dropWhile {
                !it.commentText.contains("thumbnail begin")
            }
            .takeWhile { !it.commentText.contains("thumbnail end") }
            .map(GCodeLine::commentText)
            .map(String::trim)
            .onEachIndexed { index, line ->
                if (index == 0) {
                    val rx = """(\d+)x(\d+)""".toRegex()

                    val (_, widthStr, heightStr) = rx.find(line)!!.groupValues

                    width = widthStr.toInt()
                    height = heightStr.toInt()
                }
            }
            .drop(1)
            .joinToString("")

        return Base64EncodedThumbnail(width, height, data)
    }

    private data class NumberedGCodeLine(
        val lineNumber: Int,
        val line: GCodeLine,
    )
}

data class Base64EncodedThumbnail(
    val width: Int,
    val height: Int,
    val base64data: String,
)