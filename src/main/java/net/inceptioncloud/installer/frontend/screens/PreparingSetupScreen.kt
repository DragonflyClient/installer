package net.inceptioncloud.installer.frontend.screens

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.backend.processes.preparing.*
import net.inceptioncloud.installer.frontend.FontManager
import net.inceptioncloud.installer.frontend.ProcessRenderer
import net.inceptioncloud.installer.frontend.Screen
import java.awt.Color
import java.awt.Graphics2D
import java.awt.event.MouseEvent

class PreparingSetupScreen : Screen(2) {
    /**
     * Process Renderer for the current screen.
     */
    private val processRenderer: ProcessRenderer = ProcessRenderer()

    /**
     * All processes that can potentially be executed here.
     */
    private val potentialProcesses = listOf(
        StoringOptions(),
        UninstallingOldClient(),
        UninstallingOldAssets(),
        CreatingClientFolder(),
        CreatingAssetsFolder()
    )

    /**
     * Called when switching to the screen.
     */
    init {
        if (MinecraftModInstaller.screen !is ErrorScreen) {
            Thread {
                Thread.sleep(500)
                potentialProcesses.forEach {
                    if (it.test()) {
                        processRenderer.add(it)
                        Thread.sleep(300)
                    }
                }
                processRenderer.forEach {
                    it.execute()
                    Thread.sleep(100)
                }
                processRenderer.investigateExecution()

                Thread.sleep(1000)
                MinecraftModInstaller.screen = InstallingClientScreen()
            }.start()
        }
    }

    /**
     * Called when painting the screen.
     */
    override fun paint(graphics2D: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        // Paragraph
        graphics2D.color = Color(50, 50, 50)
        FontManager.drawCenteredString("Preparing Setup", x + width / 2, y + 160, 0, 30, graphics2D)

        processRenderer.renderAll(graphics2D, x + 20, y + 225)
    }

    /**
     * Called when the mouse clicks on the screen.
     */
    override fun mouseClicked(event: MouseEvent?) {
    }

    /**
     * Called when the mouse moves.
     */
    override fun mouseMoved(event: MouseEvent?) {
    }
}