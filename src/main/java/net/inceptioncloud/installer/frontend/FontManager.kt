package net.inceptioncloud.installer.frontend

import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.GraphicsEnvironment
import java.awt.font.FontRenderContext
import java.awt.font.TextAttribute
import java.util.*


/**
 * Manages the loading and saving of all necessary fonts.
 */
object FontManager {

    /**
     * Variable to store the width of the "here" text link
     */
    var linkWidth: Int = 0

    /**
     * Variable to store the height of the "here" text link
     */
    var linkHeight: Int = 0

    /**
     * Variable to store the start-x of the "here" text link
     */
    var linkStartX: Int = 0

    /**
     * Variable to store the start-y of the "here" text link
     */
    var linkStartY: Int = 0

    /**
     * Adds all necessary fonts to the graphics environment.
     */
    fun registerFonts() {
        val fonts = arrayOf("Rubik", "Rubik-Light", "Rubik-Medium")
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()

        fonts.forEach {
            val resourceName = "/$it.ttf"

            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, javaClass.getResourceAsStream(resourceName)))
        }
    }

    /**
     * Loads the Product Sans Font with the given style and size.
     *
     * styles: <br>
     *  0 - Regular <br>
     *  1 - Medium <br>
     *  2 - Light <br>
     */
    fun loadFont(style: Int, size: Int): Font
    {
        val font = Font("Rubik" + (if (style == 1) " Medium" else if (style == 2) " Light" else ""), 0, size)
        val attributes: MutableMap<TextAttribute, Any?> = HashMap()
        attributes[TextAttribute.TRACKING] = if (style == 1) -0.04 else if (style == 2) -0.008 else 0.0
        return font.deriveFont(attributes)
    }

    /**
     * Draws a centered string.
     */
    fun drawCenteredString(str: String, x: Int, y: Int, style: Int, size: Int, graphics2D: Graphics2D)
    {
        val font = loadFont(style, size)
        graphics2D.font = font

        drawCenteredString(str, x, y, graphics2D)
    }

    /**
     * Draws a centered string.
     */
    fun drawCenteredString(str: String, x: Int, y: Int, graphics2D: Graphics2D) {
        val frc = FontRenderContext(null, true, true)
        var bounds = graphics2D.font.getStringBounds(str, frc)
        var width = bounds.width.toInt()
        var startX = x - (width / 2)

        if (str == "Dragonfly please click <click>here</click> for") {

            val textBefore = str.split("<click>")[0]
            var textMid = str.replace(textBefore, "")
            val textAfter = str.split("</click>")[1]
            textMid = textMid.replace(textAfter, "")
            textMid = textMid.replace("<click>", "")
            textMid = textMid.replace("</click>", "")

            graphics2D.color = Color(50, 50, 50)
            bounds = graphics2D.font.getStringBounds(textBefore + textMid + textAfter, frc)
            width = bounds.width.toInt()
            startX = x - (width / 2)
            graphics2D.drawString(textBefore, startX, y)

            graphics2D.color = Colors.RED_ORANGE
            bounds = graphics2D.font.getStringBounds(textBefore + textMid, frc)
            width = bounds.width.toInt()
            startX = x - (width / 2)
            graphics2D.drawString(textMid, startX + 203, y)

            linkWidth = width
            linkHeight = 15
            linkStartX = startX
            linkStartY = (y - 20)

            graphics2D.color = Color(50, 50, 50)
            bounds = graphics2D.font.getStringBounds(textBefore + textMid, frc)
            width = bounds.width.toInt()
            startX = x - (width / 2)
            graphics2D.drawString(textAfter, startX + 249, y)

        } else {
            graphics2D.drawString(str, startX, y)
        }
    }
}