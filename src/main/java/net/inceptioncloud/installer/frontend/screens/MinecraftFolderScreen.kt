package net.inceptioncloud.installer.frontend.screens

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.backend.CustomError
import net.inceptioncloud.installer.backend.FileChooser
import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.frontend.FontManager
import net.inceptioncloud.installer.frontend.Screen
import net.inceptioncloud.installer.frontend.objects.UIButton
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.MouseEvent
import java.io.File
import javax.swing.JFileChooser
import javax.swing.UIManager

class MinecraftFolderScreen : Screen(2) {

    private var minecraftFolder = InstallManager.MINECRAFT_PATH

    private val `continue`: UIButton = object : UIButton("Continue") {
        override fun buttonClicked() {
            InstallManager.MINECRAFT_PATH = minecraftFolder
            MinecraftModInstaller.screen = PreparingSetupScreen()
        }
    }

    private val select: UIButton = object : UIButton("Select") {
        override fun buttonClicked() {

            val chooser = FileChooser(
                File(System.getenv("appdata")),
                MinecraftModInstaller.window, "Select",
                JFileChooser.DIRECTORIES_ONLY,
                JFileChooser.APPROVE_OPTION,
                UIManager.getSystemLookAndFeelClassName()
            )
            minecraftFolder = File(chooser.start()?.absolutePath)

            val minecraftVersion = File("${minecraftFolder}\\versions\\1.8.8\\")

            if (!minecraftFolder.exists() && !MinecraftModInstaller.occurredErrors.contains("fileMissing/.minecraft")) {
                MinecraftModInstaller.occurredErrors.add("fileMissing/.minecraft")
                CustomError("101", "File (${minecraftFolder.absolutePath}) not found").printStackTrace()
            }

            if (!minecraftVersion.exists() && !MinecraftModInstaller.occurredErrors.contains("fileMissing/1.8-version")) {
                MinecraftModInstaller.occurredErrors.add("fileMissing/1.8-version")
                CustomError("101", "File (${minecraftVersion.absolutePath}) not found").printStackTrace()
            }
        }
    }

    init {
        childs.add(`continue`)
        childs.add(select)
    }

    override fun paint(graphics2D: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        val offset = 4

        graphics2D.color = Color(50, 50, 50)
        FontManager.drawCenteredString("Minecraft Folder", x + width / 2, y + 160, 0, 30, graphics2D)

        FontManager.drawCenteredString(
            "$minecraftFolder",
            x + width / 2 - offset,
            y + 205,
            2,
            16,
            graphics2D
        )

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