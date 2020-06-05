package net.inceptioncloud.installer.frontend.screens

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
    private val stable: UIButton = object : UIButton("Stable version") {

        override fun buttonClicked() {
            MinecraftModInstaller.screen = PreparingSetupScreen()
        }

    }

    /**
     * Button that is clicked to start the setup for the eap version.
     */
    private val earlyAccess: UIButton = object : UIButton("EAP version") {

        override fun buttonClicked() {
            downloadEAP = true
            MinecraftModInstaller.screen = PreparingSetupScreen()
        }

    }

    init {
        childs.add(stable)
        childs.add(earlyAccess)
    }

    override fun paint(graphics2D: Graphics2D, x: Int, y: Int, width: Int, height: Int) {

        graphics2D.color = Color(50, 50, 50)
        FontManager.drawCenteredString("Select Client Version", x + width / 2, y + 160, 0, 30, graphics2D)

        // Stable button
        val buttonWidth = (width / 2)
        val buttonHeight = buttonWidth / 5.1
        stable.paint(graphics2D, x + width / 2 - buttonWidth / 2, y + 350, buttonWidth, buttonHeight.toInt())
        earlyAccess.paint(graphics2D, x + width / 2 - buttonWidth / 2, y + 425, buttonWidth, buttonHeight.toInt())

    }

    override fun mouseClicked(event: MouseEvent?) {
        childs.forEach { it.mouseClicked(event) }
    }

    override fun mouseMoved(event: MouseEvent?) {
        childs.forEach { it.mouseMoved(event) }
    }

}