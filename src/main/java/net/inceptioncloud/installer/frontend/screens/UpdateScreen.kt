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

class UpdateScreen(private val newest: String) : Screen(8) {

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
            "v$newest",
            x + width / 2 - offset,
            y + 215,
            1,
            20,
            graphics2D
        )
        FontManager.drawCenteredString(
            "Please download the latest",
            x + width / 2 - offset,
            y + 277,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "version of the dragonfly installer",
            x + width / 2 - offset,
            y + 302,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "in order to keep up to date with",
            x + width / 2 - offset,
            y + 327,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "new features, hotfixes and other",
            x + width / 2 - offset,
            y + 352,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "updates.",
            x + width / 2 - offset,
            y + 377,
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