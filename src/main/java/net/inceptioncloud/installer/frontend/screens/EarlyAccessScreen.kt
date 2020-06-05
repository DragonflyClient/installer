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
            Logger.log("User selected \"Stable\" for download")
        }

    }

    /**
     * Button that is clicked to start the setup for the eap version.
     */
    private val earlyAccess: UIButton = object : UIButton("Early Access") {

        override fun buttonClicked() {
            downloadEAP = true
            MinecraftModInstaller.screen = PreparingSetupScreen()
            Logger.log("User selected \"Early Access\" for download")
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
            "Lorem ipsum dolor sit amet, consetetur",
            x + width / 2 - offset,
            y + 245,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "tempor invidunt ut labore et dolore",
            x + width / 2 - offset,
            y + 273,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "sadipscing elitr, sed diam nonumy",
            x + width / 2 - offset,
            y + 303,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "magna aliquyam erat, sed diam.",
            x + width / 2 - offset,
            y + 333,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString("At vero eos et accusam et", x + width / 2 - offset, y + 363, 2, 22, graphics2D)

        val buttonWidth = (width / 2)
        val buttonHeight = buttonWidth / 5.1
        stable.paint(graphics2D, x + width / 2 - buttonWidth / 2, y + 435, buttonWidth, buttonHeight.toInt())
        earlyAccess.paint(graphics2D, x + width / 2 - buttonWidth / 2, y + 485, buttonWidth, buttonHeight.toInt())
    }

    override fun mouseClicked(event: MouseEvent?) {
        childs.forEach { it.mouseClicked(event) }
    }

    override fun mouseMoved(event: MouseEvent?) {
        childs.forEach { it.mouseMoved(event) }
    }

}