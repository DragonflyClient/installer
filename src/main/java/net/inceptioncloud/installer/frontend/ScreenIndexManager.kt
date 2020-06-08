package net.inceptioncloud.installer.frontend

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.MinecraftModInstaller.WINDOW_HEIGHT
import net.inceptioncloud.installer.MinecraftModInstaller.WINDOW_WIDTH
import net.inceptioncloud.installer.frontend.screens.ErrorScreen
import net.inceptioncloud.installer.frontend.screens.WelcomeScreen
import net.inceptioncloud.installer.frontend.transition.number.SmoothDoubleTransition
import net.inceptioncloud.installer.frontend.transition.supplier.ForwardNothing
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.GradientPaint
import java.awt.Graphics2D

/**
 * Manages the handling of screen indexes.
 */
object ScreenIndexManager {
    /**
     * Constant for fade-in duration.
     */
    const val FADE_IN = 20

    /**
     * Constant for stay duration.
     */
    const val STAY = 10

    /**
     * Constant for fade-out duration.
     */
    const val FADE_OUT = 20

    /**
     * List with all transitions for the different index switches.
     */
    private val allTransitions = listOf(
        SmoothDoubleTransition.builder().start(0.0).end(1.0).fadeIn(FADE_IN).stay(STAY).fadeOut(FADE_OUT)
            .autoTransformator(ForwardNothing { currentIndex == 1 }).build(),
        SmoothDoubleTransition.builder().start(0.0).end(1.0).fadeIn(FADE_IN).stay(STAY).fadeOut(FADE_OUT)
            .autoTransformator(ForwardNothing { currentIndex == 2 }).build(),
        SmoothDoubleTransition.builder().start(0.0).end(1.0).fadeIn(FADE_IN).stay(STAY).fadeOut(FADE_OUT)
            .autoTransformator(ForwardNothing { currentIndex == 3 }).build(),
        SmoothDoubleTransition.builder().start(0.0).end(1.0).fadeIn(FADE_IN).stay(STAY).fadeOut(FADE_OUT)
            .autoTransformator(ForwardNothing { currentIndex == 4 }).build(),
        SmoothDoubleTransition.builder().start(0.0).end(1.0).fadeIn(FADE_IN).stay(STAY).fadeOut(FADE_OUT)
            .autoTransformator(ForwardNothing { currentIndex == 5 }).build(),
        SmoothDoubleTransition.builder().start(0.0).end(1.0).fadeIn(FADE_IN).stay(STAY).fadeOut(FADE_OUT)
            .autoTransformator(ForwardNothing { currentIndex == 6 }).build()
    )

    /**
     * Current Screen Index.
     */
    var currentIndex = 0

    /**
     * Distance between two circles in pixels.
     */
    private const val DISTANCE = 17

    /**
     * Cross-section of the inner circle.
     */
    private const val INNER_CIRCLE = 6

    /**
     * Cross-section of the outer circle.
     */
    private const val OUTER_CIRCLE = 12

    /**
     * Draws the points at the bottom that indicate the screen index.
     */
    fun drawStepIndex(graphics2D: Graphics2D) {
        val transition = MinecraftModInstaller.screenSwitch.get()
        var currentFloat = (transition / 400).toFloat()

        if (MinecraftModInstaller.screen is ErrorScreen) {
            if(transition == 400.0)
                currentFloat = 0.0F

            graphics2D.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentFloat)
        }

        val center = WINDOW_WIDTH / 2
        val startX = (center - OUTER_CIRCLE * 3.5 - DISTANCE * 3).toInt()
        val borderRadius = (OUTER_CIRCLE - INNER_CIRCLE) / 2
        val width = OUTER_CIRCLE * 6 + DISTANCE * 5
        val y = (WINDOW_HEIGHT - 40) + WelcomeScreen.buttonFlyIn.castToInt()

        var currentCircleX = startX

        for (index in 0..6) {

            graphics2D.paint = GradientPaint(
                startX.toFloat(),
                y.toFloat(),
                Colors.RED,
                (startX + width).toFloat(),
                y.toFloat(),
                Colors.ORANGE
            )
            graphics2D.fillOval(currentCircleX, y, OUTER_CIRCLE, OUTER_CIRCLE)

            graphics2D.color = Color.WHITE
            graphics2D.fillOval(currentCircleX + borderRadius, y + borderRadius, INNER_CIRCLE, INNER_CIRCLE)
            currentCircleX += OUTER_CIRCLE + DISTANCE
        }

        var selectedX = startX + borderRadius
        getCurrentTransition()?.let {
            selectedX += ((currentIndex - 1) * (OUTER_CIRCLE + DISTANCE) + it.get() * (OUTER_CIRCLE + DISTANCE)).toInt()
        }

        graphics2D.paint = GradientPaint(
            startX.toFloat(),
            y.toFloat(),
            Colors.RED,
            (startX + width).toFloat(),
            y.toFloat(),
            Colors.ORANGE
        )
        graphics2D.fillOval(selectedX - 2, y + borderRadius - 2, INNER_CIRCLE + 4, INNER_CIRCLE + 4)

        graphics2D.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0F)
    }

    /**
     * Returns the current transition based on the current index.
     */
    private fun getCurrentTransition(): SmoothDoubleTransition? {
        return allTransitions.firstOrNull { it.autoTransformator.asInt == 1 }
    }
}