package net.inceptioncloud.installer.frontend

import java.awt.Graphics2D
import java.awt.event.MouseEvent

/**
 * A simple abstract class that provides certain screen methods.
 */
abstract class Screen(val stepIndex: Int = -1)
{
    /**
     * List of every screen-child. (eg. buttons, text fields, etc.)
     */
    val childs = mutableListOf<Screen>()

    /**
     * Called when painting the screen.
     */
    abstract fun paint (graphics2D: Graphics2D, x: Int, y: Int, width: Int, height: Int)

    /**
     * Called when the mouse clicks on the screen.
     */
    abstract fun mouseClicked(event: MouseEvent?)

    /**
     * Called when the mouse moves.
     */
    abstract fun mouseMoved(event: MouseEvent?)
}