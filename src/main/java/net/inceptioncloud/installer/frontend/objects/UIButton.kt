package net.inceptioncloud.installer.frontend.objects

import net.inceptioncloud.installer.frontend.Colors
import net.inceptioncloud.installer.frontend.FontManager
import net.inceptioncloud.installer.frontend.Screen
import net.inceptioncloud.installer.frontend.transition.color.ColorTransition
import net.inceptioncloud.installer.frontend.transition.supplier.ForwardBackward
import java.awt.Color
import java.awt.GradientPaint
import java.awt.Graphics2D
import java.awt.event.MouseEvent

/**
 * A simple user interface button that can be used as a screen-child.
 */
open class UIButton(var text: String) : Screen() {
    // The bounds in which the button was painted the last time.
    private var x: Int = 0
    private var y: Int = 0
    private var width: Int = 0
    private var height: Int = 0

    /**
     * Whether the button is currently hovered.
     */
    private var hovered: Boolean = false

    private var secondaryStart = ColorTransition.builder()
        .start(Color(0xFFFFFF)).end(Colors.RED)
        .amountOfSteps(50).autoTransformator(ForwardBackward { hovered })
        .build()

    private var secondaryEnd = ColorTransition.builder()
        .start(Color(0xFFFFFF)).end(Colors.ORANGE)
        .amountOfSteps(50).autoTransformator(ForwardBackward { hovered })
        .build()

    private var primaryStart = ColorTransition.builder()
        .start(Colors.RED).end(Color(0xFFFFFF))
        .amountOfSteps(20).autoTransformator(ForwardBackward { hovered })
        .build()

    private var primaryEnd = ColorTransition.builder()
        .start(Colors.ORANGE).end(Color(0xFFFFFF))
        .amountOfSteps(20).autoTransformator(ForwardBackward { hovered })
        .build()

    /**
     * Called when painting the screen.
     */
    override fun paint(graphics2D: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        val startX = x.toFloat()
        val startY = (y + height).toFloat()
        val endX = (x + width).toFloat()
        val endY = y.toFloat()

        // Outline
        graphics2D.paint = GradientPaint(startX, startY, secondaryStart.get(), endX, endY, secondaryEnd.get())
        graphics2D.fillRoundRect(x - 2, y - 2, width + 4, height + 4, 25, 25)

        // Inner
        graphics2D.paint = GradientPaint(startX, startY, primaryStart.get(), endX, endY, primaryEnd.get())
        graphics2D.fillRoundRect(x, y, width, height, 20, 20)

        // Text
        graphics2D.paint = GradientPaint(startX, startY, secondaryStart.get(), endX, endY, secondaryEnd.get())
        graphics2D.font = FontManager.loadFont(0, (height / 1.7).toInt())
        FontManager.drawCenteredString(
            text,
            x + width / 2,
            y + height / 2 + graphics2D.fontMetrics.height / 4 + 2,
            graphics2D
        )

        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    /**
     * An empty on-click function that can be overwritten.
     */
    open fun buttonClicked() {
    }

    /**
     * Called when the mouse clicks on the screen.
     */
    override fun mouseClicked(event: MouseEvent?) {
        event?.let {
            if (event.x >= x && event.x <= x + width && event.y >= y && event.y <= y + height) {
                buttonClicked()
            }
        }
    }

    /**
     * Called when the mouse moves.
     */
    override fun mouseMoved(event: MouseEvent?) {
        event?.let {
            hovered = it.x >= x && it.x <= x + width && it.y >= y && it.y <= y + height
        }
    }
}