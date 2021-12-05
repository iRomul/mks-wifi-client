package io.github.iromul.mkstransfer.app.io.gcode

import io.github.iromul.mkstransfer.app.io.gcode.thumbnails.MksTftThumbnailWriter
import io.github.iromul.mkstransfer.app.io.gcode.thumbnails.PrusaSlicerEmbeddedThumbnailReader
import io.github.iromul.mkstransfer.app.io.gcode.thumbnails.Size
import org.junit.jupiter.api.Test
import java.io.File
import java.util.Base64

internal class PrusaSlicerEmbeddedThumbnailReaderTest2 {

    @Test
    internal fun `export files`() {
        val file = File(javaClass.classLoader.getResource("Crab6_1_with_Chain_Holder.gcode")!!.toURI())
        val gCodeFile = GCodeFileReader.load(file.reader())

        val reader = PrusaSlicerEmbeddedThumbnailReader(gCodeFile)

        val thumbnails = reader.getAllEncodedThumbnails()

        thumbnails.forEach {
            val img = Base64.getDecoder().decode(it.base64data)

            File("thumbnail_${it.width}x${it.height}.png").writeBytes(img)
        }

        val encodedThumbnails = reader.getAllThumbnails()

        gCodeFile.trimHead()

        val mksTftThumbnailWriter = MksTftThumbnailWriter(gCodeFile, Size(50, 50), Size(200, 200))

        mksTftThumbnailWriter.writeAllThumbnails(encodedThumbnails)

//        GCodeFileReader.save(gCodeFile, File("out.gcode"))
    }
}