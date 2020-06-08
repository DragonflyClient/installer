package net.inceptioncloud.installer.frontend.screens

import net.inceptioncloud.installer.Logger
import net.inceptioncloud.installer.frontend.FontManager
import net.inceptioncloud.installer.frontend.Screen
import net.inceptioncloud.installer.frontend.objects.UIButton
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import kotlin.system.exitProcess

class ErrorScreen : Screen(7) {

    /**
     * Current error code. Set this before switching to this screen
     */
    var currentErrorCode: Int = 404

    /**
     * Button that is clicked to start the setup for the stable version.
     */
    private val button: UIButton = object : UIButton("Close!") {

        override fun buttonClicked() {
            Logger.log("Closing wizard...")
            exitProcess(0)
        }

    }

    init {
        childs.add(button)
    }

    override fun paint(graphics2D: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        val offset = 4

        graphics2D.color = Color(50, 50, 50)
        FontManager.drawCenteredString("Installation failed", x + width / 2, y + 214, 1, 36, graphics2D)

        FontManager.drawCenteredString(
            "Error Code $currentErrorCode",
            x + width / 2 - offset,
            y + 238,
            1,
            20,
            graphics2D
        )
        FontManager.drawCenteredString(
            "An error occurred while installing",
            x + width / 2 - offset,
            y + 300,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "Dragonfly please click here for",
            x + width / 2 - offset,
            y + 325,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "more information!",
            x + width / 2 - offset,
            y + 350,
            2,
            22,
            graphics2D
        )

        val buttonWidth = (width / 2)
        val buttonHeight = buttonWidth / 5.1
        button.paint(graphics2D, x + width / 2 - buttonWidth / 2, y + 500, buttonWidth, buttonHeight.toInt())
    }

    override fun mouseClicked(event: MouseEvent?) {
        childs.forEach { it.mouseClicked(event) }
    }

    override fun mouseMoved(event: MouseEvent?) {
        childs.forEach { it.mouseMoved(event) }
    }
}