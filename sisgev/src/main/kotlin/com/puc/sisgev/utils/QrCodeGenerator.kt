package com.puc.sisgev.utils

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageConfig
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import java.awt.Color
import java.awt.font.FontRenderContext
import java.awt.font.LineBreakMeasurer
import java.awt.font.TextAttribute
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.AttributedString
import javax.imageio.ImageIO

class QrCodeGenerator {

    fun generator(productId: String, productName: String, content: String): ByteArray {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val pngOutputStream = ByteArrayOutputStream()

        val size = 512
        val qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, MatrixToImageConfig(Color.BLACK.rgb, Color.WHITE.rgb))
        val resultImage = appendCaptionToImage(size, productName, qrImage)

        ImageIO.write(resultImage, "png", pngOutputStream)

        val file = File("/home/alex.correa/ESTUDOS/PUC_MINAS/output/$productId.png")
        val fos = FileOutputStream(file)

        fos.write(pngOutputStream.toByteArray())
        fos.flush()
        fos.close()

        return pngOutputStream.toByteArray()
    }

    private fun appendCaptionToImage(
        size: Int,
        text: String,
        qrImage: BufferedImage
    ): BufferedImage {
        val textImage = BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
        val g2 = textImage.createGraphics()
        g2.color = Color.WHITE
        g2.fillRect(0, 0, textImage.width, textImage.height)
        g2.color = Color.BLACK

        val attributedString = AttributedString(text)
        attributedString.addAttribute(TextAttribute.FOREGROUND, Color.BLACK)

        val charIterator = attributedString.iterator
        val fontContext = FontRenderContext(null, true, false)
        val lineBreakMeasurer = LineBreakMeasurer(charIterator, fontContext)

        var drawPosY = 0f
        while (lineBreakMeasurer.position < charIterator.endIndex) {
            val nextLayout = lineBreakMeasurer.nextLayout(size.toFloat())
            val deltaWidth = size.toFloat() - nextLayout.bounds.width.toFloat()
            drawPosY += nextLayout.ascent
            nextLayout.draw(g2, deltaWidth / 2, drawPosY)
            drawPosY += nextLayout.descent + nextLayout.leading
        }

        val resultImage = BufferedImage(size, size + drawPosY.toInt(), BufferedImage.TYPE_INT_RGB)
        val resultGraphics = resultImage.createGraphics()
        resultGraphics.drawImage(qrImage, 0, 0, null)
        resultGraphics.drawImage(textImage, 0, size, null)
        return resultImage
    }
}
