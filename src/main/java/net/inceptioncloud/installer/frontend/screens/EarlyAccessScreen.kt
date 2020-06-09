package net.inceptioncloud.installer.frontend.screens

import net.inceptioncloud.installer.Logger
import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.frontend.FontManager
import net.inceptioncloud.installer.frontend.Screen
import net.inceptioncloud.installer.frontend.objects.UIButton
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class EarlyAccessScreen : Screen(1) {

    /**
     * Object with boolean which is used to store if the user has selected the eap-version for the downloads
     */
    companion object {
        var downloadEAP = false
    }

    /**
     * Button that is clicked to start the setup for the stable version.
     */
    private val stable: UIButton = object : UIButton("Stable") {

        override fun buttonClicked() {
            MinecraftModInstaller.screen = PreparingSetupScreen()
            Logger.log("User selected stable for download")
        }

    }

    /**
     * Button that is clicked to start the setup for the eap version.
     */
    private val earlyAccess: UIButton = object : UIButton("Early Access") {

        override fun buttonClicked() {
            downloadEAP = true
            MinecraftModInstaller.screen = PreparingSetupScreen()
            Logger.log("User selected eap for download")
        }

    }

    init {
        childs.add(stable)
        childs.add(earlyAccess)
    }

    override fun paint(graphics2D: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        val offset = 4

        graphics2D.color = Color(50, 50, 50)
        FontManager.drawCenteredString("Download Channel", x + width / 2, y + 160, 0, 30, graphics2D)

        FontManager.drawCenteredString(
            "To always have access to the latest",
            x + width / 2 - offset,
            y + 205,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "state of the art features, join our",
            x + width / 2 - offset,
            y + 230,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "Early Access Program. Please note that",
            x + width / 2 - offset,
            y + 255,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "these versions can contain several",
            x + width / 2 - offset,
            y + 280,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "bugs and are not always stable ",
            x + width / 2 - offset,
            y + 305,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "while the Stable Channel delivers",
            x + width / 2 - offset,
            y + 330,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "only safe and production-ready",
            x + width / 2 - offset,
            y + 355,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "Channel versions of the client.",
            x + width / 2 - offset,
            y + 380,
            2,
            22,
            graphics2D
        )

        val buttonWidth = (width / 2)
        val buttonHeight = buttonWidth / 5.1
        earlyAccess.paint(graphics2D, x + width / 2 - buttonWidth / 2, y + 450, buttonWidth, buttonHeight.toInt())
        stable.paint(graphics2D, x + width / 2 - buttonWidth / 2, y + 500, buttonWidth, buttonHeight.toInt())
    }

    override fun mouseClicked(event: MouseEvent?) {
        childs.forEach { it.mouseClicked(event) }
    }

    override fun mouseMoved(event: MouseEvent?) {
        childs.forEach { it.mouseMoved(event) }
    }

}