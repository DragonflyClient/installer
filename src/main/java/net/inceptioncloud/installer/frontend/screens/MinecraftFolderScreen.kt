package net.inceptioncloud.installer.frontend.screens

import net.inceptioncloud.installer.Logger
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

    /**
     * This variable is used to store the selected minecraft home directory
     */
    private var minecraftFolder: File? = null

    /**
     * This variable is used to store which text and buttons is going to be drawn
     *
     * 0 -> Unknown
     * 1 -> Autom. detected
     * 2 -> Not autom. detected
     */
    private var textSwitch = 0

    private val `continue`: UIButton = object : UIButton("Continue") {
        override fun buttonClicked() {
            if (minecraftFolder != null) {
                InstallManager.MINECRAFT_PATH = minecraftFolder!!
                MinecraftModInstaller.screen = PreparingSetupScreen()
            }
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

            try {
                val result = File(chooser.start()?.absolutePath!!)
                Logger.log("User selected \"${result.absolutePath}\" as his minecraft folder")
                minecraftFolder = result
                val minecraftVersion = File("${minecraftFolder}\\versions\\1.8.8\\")

                if (!minecraftVersion.exists() && !MinecraftModInstaller.occurredErrors.contains("fileMissing/1.8-version")) {
                    MinecraftModInstaller.occurredErrors.add("fileMissing/1.8-version")
                    CustomError("101", "File (${minecraftVersion.absolutePath}) not found").printStackTrace()
                }

                if (minecraftFolder != null) {
                    InstallManager.MINECRAFT_PATH = minecraftFolder!!
                    MinecraftModInstaller.screen = PreparingSetupScreen()
                }
            } catch (e: Exception) {
            }
        }
    }

    init {
        childs.add(`continue`)
        childs.add(select)

        if (InstallManager.MINECRAFT_PATH.exists()) {
            textSwitch = 1
            select.text = "Change"
            minecraftFolder = InstallManager.MINECRAFT_PATH
        } else {
            textSwitch = 2
        }
    }

    override fun paint(graphics2D: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        val offset = 4
        val buttonWidth = (width / 2)
        val buttonHeight = buttonWidth / 5.1

        graphics2D.color = Color(50, 50, 50)
        FontManager.drawCenteredString("Minecraft Folder", x + width / 2, y + 160, 0, 30, graphics2D)

        if (textSwitch == 1) {
            FontManager.drawCenteredString(
                "Detected: $minecraftFolder",
                x + width / 2 - offset,
                y + 205,
                2,
                16,
                graphics2D
            )
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
        } else if (textSwitch == 2) {
            FontManager.drawCenteredString(
                "Selected: $minecraftFolder",
                x + width / 2 - offset,
                y + 205,
                2,
                16,
                graphics2D
            )
            select.paint(
                graphics2D,
                x + width / 2 - buttonWidth / 2,
                y + 500 + WelcomeScreen.buttonFlyIn.castToInt(),
                buttonWidth,
                buttonHeight.toInt()
            )
        }
    }

    override fun mouseClicked(event: MouseEvent?) {
        childs.forEach { it.mouseClicked(event) }

    }

    override fun mouseMoved(event: MouseEvent?) {
        childs.forEach { it.mouseMoved(event) }
    }

}