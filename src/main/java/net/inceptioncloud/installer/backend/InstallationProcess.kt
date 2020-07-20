package net.inceptioncloud.installer.backend

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.frontend.FontManager
import net.inceptioncloud.installer.frontend.transition.color.ColorTransition
import net.inceptioncloud.installer.frontend.transition.number.DoubleTransition
import net.inceptioncloud.installer.frontend.transition.number.SmoothDoubleTransition
import net.inceptioncloud.installer.frontend.transition.supplier.AlwaysForward
import net.inceptioncloud.installer.frontend.transition.supplier.ForwardNothing
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Arc2D
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

/**
 * Represents a single step (process) during the installation.
 */
abstract class InstallationProcess(private val name: String) {
    /**
     * Whether the process is currently being rendered.
     */
    var rendering: Boolean = false

    /**
     * Status of the Installation Process
     *
     * 0: Running
     * 1: Done
     * -1: Error
     */
    var status: Int = 0

    /**
     * Icon to display when the process has finished.
     */
    private var finishIcon: BufferedImage? = null

    /**
     * Icon to display when an error occurred.
     */
    private var errorIcon: BufferedImage? = null

    /**
     * Entrance fly-in transition.
     */
    private var flyIn = SmoothDoubleTransition.builder()
        .start(1.0).end(0.0)
        .fadeIn(0).stay(10).fadeOut(10)
        .autoTransformator(ForwardNothing { rendering }).build()

    /**
     * Loading circle transition.
     */
    private var loadingTransition = DoubleTransition.builder()
        .start(180.0).end(0.0)
        .amountOfSteps(50)
        .autoTransformator(AlwaysForward()).build()

    /**
     * Loading circle close transition.
     */
    private val closeTransition = SmoothDoubleTransition.builder()
        .start(15.0).end(0.0)
        .fadeIn(20).stay(10).fadeOut(20)
        .autoTransformator(ForwardNothing { status != 0 }).build()

    /**
     * Icon entrance zoom transition.
     */
    private val iconTransition = SmoothDoubleTransition.builder()
        .start(0.0).end(1.0)
        .fadeIn(10).stay(5).fadeOut(0)
        .autoTransformator(ForwardNothing { status != 0 }).build()

    /**
     * Color finish transition.
     */
    private val finishTransition = ColorTransition.builder()
        .start(Color(50, 50, 50)).end(Color(0x34CA00))
        .amountOfSteps(20)
        .autoTransformator(ForwardNothing { status == 1 }).build()

    /**
     * Color error transition.
     */
    private val errorTransition = ColorTransition.builder()
        .start(Color(50, 50, 50)).end(Color(0xD11F1F))
        .amountOfSteps(20)
        .autoTransformator(ForwardNothing { status == -1 }).build()
    //</editor-fold>

    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    abstract fun test(): Boolean

    /**
     * Executes the download / installation that the process is responsible for.
     */
    abstract fun execute()

    /**
     * Renders the installation process at the given
     */
    fun render(graphics2D: Graphics2D, x: Int, y: Int) {
        val loading = loadingTransition.castToInt()
        val close = closeTransition.castToInt()
        val arc1 = Arc2D.Double(
            x.toDouble() - flyIn.get() * 20,
            (y - 20).toDouble(),
            24.0,
            24.0,
            15.0 - close + loading,
            180.0 - close * 3,
            Arc2D.PIE
        )
        val arc2 = Arc2D.Double(
            x.toDouble() - flyIn.get() * 20,
            (y - 20).toDouble(),
            24.0,
            24.0,
            195.0 - close + loading,
            180.0 - close * 3,
            Arc2D.PIE
        )

        graphics2D.color = if (status == -1) errorTransition.get() else finishTransition.get()
        graphics2D.fill(arc1)
        graphics2D.fill(arc2)

        graphics2D.color = Color.WHITE
        graphics2D.fillOval((x + 2 - flyIn.get() * 20).toInt(), y - 18, 20, 20)

        graphics2D.font = FontManager.loadFont(2, 24)
        graphics2D.color = Color(50, 50, 50, ((1 - flyIn.get()) * 255).toInt())
        graphics2D.drawString(name, x + 40, (y + 1 + flyIn.get() * 20).toInt())

        val centerX = x + 12
        val centerY = y - 8
        val size = (iconTransition.get() * 16).toInt()

        try {
            finishIcon = ImageIO.read(javaClass.getResourceAsStream("/status/finish.png"))
        } catch (e: Exception) {
            MinecraftModInstaller.reportError("201", "Image (resources/finish.png) not found")
        }

        try {
            errorIcon = ImageIO.read(javaClass.getResourceAsStream("/status/error.png"))
        } catch (e: Exception) {
            MinecraftModInstaller.reportError("201", "Image (resources/error.png) not found")
        }

        graphics2D.drawImage(
            if (status == -1) errorIcon else finishIcon,
            centerX - size / 2,
            centerY - size / 2,
            size,
            size,
            null
        )

        if (loadingTransition.isAtEnd) {
            loadingTransition = DoubleTransition.builder()
                .start(180.0).end(0.0)
                .amountOfSteps(50)
                .autoTransformator(AlwaysForward()).build()
        }
    }
}