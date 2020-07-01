package net.inceptioncloud.installer.frontend.screens

import net.inceptioncloud.installer.CacheManager
import net.inceptioncloud.installer.Logger
import net.inceptioncloud.installer.frontend.FontManager
import net.inceptioncloud.installer.frontend.Screen
import net.inceptioncloud.installer.frontend.objects.UIButton
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.io.File
import kotlin.system.exitProcess

/**
 * First Screen that is shown when opening the installer.
 */
class FinishedScreen : Screen(7) {

    /**
     * File of the minecraft launcher
     */
    private lateinit var launcher: File

    /**
     * Button that is clicked to start the setup.
     */
    private val button: UIButton = object : UIButton("Close") {
        override fun buttonClicked() {

            if (this.text == "Play now!") {
                ProcessBuilder(launcher.absolutePath).start()
                Logger.log("Closing wizard and starting minecraft launcher...")
            }

            CacheManager.deleteFolder()
            exitProcess(0)
        }
    }

    init {
        childs.add(button)
        Logger.log("Installation successful!")

        launcher = File("${System.getenv("ProgramFiles")} (x86)\\Minecraft Launcher\\MinecraftLauncher.exe")

        if (launcher.exists()) {
            button.text = "Play now!"
        }
    }

    /**
     * Called when painting the screen.
     */
    override fun paint(graphics2D: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        val offset = 4

        // Paragraph
        graphics2D.color = Color(50, 50, 50)
        FontManager.drawCenteredString("Finished", x + width / 2 - offset, y + 160, 0, 30, graphics2D)

        FontManager.drawCenteredString(
            "The Inception Cloud Dragonfly Mod",
            x + width / 2 - offset,
            y + 205,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "has been successfully installed!",
            x + width / 2 - offset,
            y + 233,
            2,
            22,
            graphics2D
        )

        FontManager.drawCenteredString(
            "Open your Minecraft Launcher and",
            x + width / 2 - offset,
            y + 280,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString(
            "select the \"Dragonfly\"",
            x + width / 2 - offset,
            y + 308,
            2,
            22,
            graphics2D
        )
        FontManager.drawCenteredString("profile to run the client.", x + width / 2 - offset, y + 336, 2, 22, graphics2D)

        // Button
        val buttonWidth = (width / 2)
        val buttonHeight = buttonWidth / 5.1
        button.paint(graphics2D, x + width / 2 - buttonWidth / 2, y + 500, buttonWidth, buttonHeight.toInt())
    }

    /**
     * Called when the mouse clicks on the screen.
     */
    override fun mouseClicked(event: MouseEvent?) {
        childs.forEach { it.mouseClicked(event) }
    }

    /**
     * Called when the mouse moves.
     */
    override fun mouseMoved(event: MouseEvent?) {
        childs.forEach { it.mouseMoved(event) }
    }
}