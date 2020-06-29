package net.inceptioncloud.installer.frontend.screens

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.frontend.FontManager
import net.inceptioncloud.installer.frontend.Screen
import net.inceptioncloud.installer.frontend.objects.UIButton
import java.awt.Color
import java.awt.Desktop
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.net.URI
import java.util.*
import kotlin.system.exitProcess

class ErrorScreen(private val currentErrorCode: Int) : Screen(7) {

    /**
     * Button that is clicked to start the setup for the stable version.
     */
    private val button: UIButton = object : UIButton("Close") {

        override fun buttonClicked() {
            exitProcess(0)
        }

    }

    init {
        childs.add(button)

        if (!MinecraftModInstaller.tabOpen) {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    MinecraftModInstaller.tabOpen = true
                    Desktop.getDesktop()
                        .browse(URI("https://inceptioncloud.net/dragonfly/installer/errors#$currentErrorCode"))
                }
            }, 1000)
        }
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
            "Dragonfly please click <click>here</click> for",
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
        if (event != null) {
            if (event.y > FontManager.linkStartY && event.y < (FontManager.linkStartY + FontManager.linkHeight)) {
                if (event.x > FontManager.linkStartX && event.x < (FontManager.linkStartX + FontManager.linkWidth)) {
                    Desktop.getDesktop()
                        .browse(URI("https://inceptioncloud.net/dragonfly/installer/errors#$currentErrorCode"))
                }
            }
        }

        childs.forEach { it.mouseClicked(event) }
    }

    override fun mouseMoved(event: MouseEvent?) {
        childs.forEach { it.mouseMoved(event) }
    }
}