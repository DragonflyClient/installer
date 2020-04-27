package net.inceptioncloud.installer.frontend.objects

import net.inceptioncloud.installer.frontend.Screen
import net.inceptioncloud.installer.frontend.transition.number.SmoothDoubleTransition
import net.inceptioncloud.installer.frontend.transition.supplier.ForwardBackward
import java.awt.Graphics2D
import java.awt.Image
import java.awt.event.MouseEvent

/**
 * A simple clickable image in an UI.
 */
open class UIImage(private val image: Image) : Screen()
{
    // The bounds in which the button was painted the last time.
    private var x: Int = 0
    private var y: Int = 0
    private var width: Int = 0
    private var height: Int = 0

    /**
     * Whether the buton is currently hovered.
     */
    private var hovered: Boolean = false

    /**
     * Transition that changes the size of the image on hover.
     */
    private var sizeTransition = SmoothDoubleTransition.builder()
        .start(2.0).end(-2.0)
        .fadeIn(5).stay(5).fadeOut(5)
        .autoTransformator(ForwardBackward { hovered }).build()

    /**
     * Called when painting the screen.
     */
    override fun paint(graphics2D: Graphics2D, x: Int, y: Int, width: Int, height: Int)
    {
        this.x = x
        this.y = y
        this.width = width
        this.height = height

        val sizeMod = sizeTransition.castToInt()

        graphics2D.drawImage(image, x + sizeMod, y + sizeMod, width - sizeMod * 2, height - sizeMod * 2, null)
    }

    /**
     * Called when the mouse clicks on the screen.
     */
    override fun mouseClicked(event: MouseEvent?)
    {
        event?.let {
            if (event.x >= x && event.x <= x + width && event.y >= y && event.y <= y + height)
            {
                buttonClicked()
            }
        }
    }

    /**
     * Called when the mouse moves.
     */
    override fun mouseMoved(event: MouseEvent?)
    {
        event?.let {
            hovered = it.x >= x && it.x <= x + width && it.y >= y && it.y <= y + height
        }
    }

    /**
     * An empty on-click function that can be overwritten.
     */
    open fun buttonClicked()
    {
    }
}