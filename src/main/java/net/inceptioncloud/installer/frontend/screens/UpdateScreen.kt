package net.inceptioncloud.installer.frontend.screens

import net.inceptioncloud.installer.frontend.FontManager
import net.inceptioncloud.installer.frontend.Screen
import net.inceptioncloud.installer.frontend.objects.UIButton
import java.awt.Color
import java.awt.Desktop
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.net.URI
import kotlin.system.exitProcess

class UpdateScreen(private val newVersion: String) : Screen(8) {

    /**
     * Button that is clicked to start the setup for the stable version.
     */
    private val button: UIButton = object : UIButton("Download") {

        override fun buttonClicked() {
            Desktop.getDesktop()
                .browse(URI("https://inceptioncloud.net/dragonfly/download"))
            exitProcess(0)
        }

    }

    init {
        childs.add(button)
    }

    override fun paint(graphics2D: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        val offset = 4

        graphics2D.color = Color(50, 50, 50)
        FontManager.drawCenteredString("Update available", x + width / 2, y + 191, 1, 36, graphics2D)

        FontManager.drawCenteredString(
            "v$newVersion",
            x + width / 2 - offset,
            y + 215,
            1,
            20,
            graphics2D
        )
        FontManager.drawCenteredString(
            "To use the installer, you need to",
            x + width / 2 - offset,
            y + 277,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "download the latest version.",
            x + width / 2 - offset,
            y + 302,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "Since the installation may not be",
            x + width / 2 - offset,
            y + 362,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "carried out correctly with this",
            x + width / 2 - offset,
            y + 388,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "version.",
            x + width / 2 - offset,
            y + 412,
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