package net.inceptioncloud.installer.frontend

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.MinecraftModInstaller.WINDOW_WIDTH
import net.inceptioncloud.installer.backend.InstallationProcess
import net.inceptioncloud.installer.frontend.transition.number.SmoothDoubleTransition
import net.inceptioncloud.installer.frontend.transition.supplier.ForwardNothing
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D

/**
 * List of all installation processes that are rendered.
 */
class ProcessRenderer : ArrayList<InstallationProcess>()
{
    /**
     * True if the execution of a process threw an error.
     */
    private var failed = false

    /**
     * Transition that moves the fail message on the screen!
     */
    private val failedTransition = SmoothDoubleTransition.builder()
            .start(1.0).end(0.0)
            .fadeIn(0).stay(30).fadeOut(30)
            .autoTransformator(ForwardNothing { failed }).build()

    /**
     * Calls the render function on all
     */
    fun renderAll(graphics2D: Graphics2D, x: Int, startY: Int)
    {
        var currentY = startY

        ArrayList(this).forEach {
            it.rendering = true
            it.render(graphics2D, x, currentY)

            currentY += 36
        }

        if (failed)
        {
            val failedY = startY + size * 36 + 30

            graphics2D.color = Color(50, 50, 50)
            graphics2D.stroke = BasicStroke(1.5F)
            graphics2D.drawLine(x, failedY - 25, (x + (WINDOW_WIDTH - 40) * (1 - failedTransition.get())).toInt(), failedY - 25)

            graphics2D.color = Color(0xD11F1F)
            graphics2D.font = FontManager.loadFont(1, 24)
            FontManager.drawCenteredString(
                "Installation failed",
                x + (WINDOW_WIDTH - 40) / 2 - (failedTransition.get() * WINDOW_WIDTH).toInt(),
                failedY + 5,
                graphics2D
            )
        }
    }

    /**
     * Investigates the processes' status codes after their execution.
     */
    fun investigateExecution()
    {
        val error = any { it.status == -1 }
        val finished = none { it.status != 1 }

        if (error)
        {
            for (process in this) {
                if (process.status == -1)
                    failed = true
                else if (failed)
                    process.status = -1
            }

            Thread.sleep(10_000)
        }

        if (!finished) {
            if (MinecraftModInstaller.errorTypes.isEmpty()) {
                throw IllegalStateException("Each process should either end with status code -1 or 1!")
            } else {
                throw InterruptedException("An custom error occurred!")
            }
        }
    }
}