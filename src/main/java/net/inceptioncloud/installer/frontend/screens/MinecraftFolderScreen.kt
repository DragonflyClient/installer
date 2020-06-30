package net.inceptioncloud.installer.frontend.screens

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.frontend.FontManager
import net.inceptioncloud.installer.frontend.Screen
import net.inceptioncloud.installer.frontend.objects.UIButton
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class MinecraftFolderScreen : Screen(2) {

    private val `continue`: UIButton = object : UIButton("Continue") {
        override fun buttonClicked() {
            MinecraftModInstaller.screen = PreparingSetupScreen()
        }
    }

    private val select: UIButton = object : UIButton("Select") {
        override fun buttonClicked() {
            // Open fileBrowser
        }
    }

    init {
        childs.add(`continue`)
        childs.add(select)
    }

    override fun paint(graphics2D: Graphics2D, x: Int, y: Int, width: Int, height: Int) {

        graphics2D.color = Color(50, 50, 50)
        FontManager.drawCenteredString("Minecraft Folder", x + width / 2, y + 160, 0, 30, graphics2D)

        val buttonWidth = (width / 2)
        val buttonHeight = buttonWidth / 5.1
        select.paint(
            graphics2D,
            x + width / 2 - buttonWidth / 2,
            y + 450 + WelcomeScreen.buttonFlyIn.castToInt(),
            buttonWidth,
            buttonHeight.toInt()
        )
        `continue`.paint(
            graphics2D,
            x + width / 2 - buttonWidth / 2,
            y + 500 + WelcomeScreen.buttonFlyIn.castToInt(),
            buttonWidth,
            buttonHeight.toInt()
        )
    }

    override fun mouseClicked(event: MouseEvent?) {
        childs.forEach { it.mouseClicked(event) }

    }

    override fun mouseMoved(event: MouseEvent?) {
        childs.forEach { it.mouseMoved(event) }
    }

}