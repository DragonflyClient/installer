package net.inceptioncloud.installer

import dev.bytenet.lib.graphics.GraphicsLibrary
import dev.bytenet.lib.graphics.transitions.number.SmoothDoubleTransition
import dev.bytenet.lib.graphics.transitions.supplier.ForwardNothing
import net.inceptioncloud.installer.frontend.FontManager
import net.inceptioncloud.installer.frontend.FontManager.registerFonts
import net.inceptioncloud.installer.frontend.Screen
import net.inceptioncloud.installer.frontend.ScreenIndexManager
import net.inceptioncloud.installer.frontend.screens.WelcomeScreen
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * Core of the Minecraft Mod Installer...
 */
object MinecraftModInstaller
{
    /**
     * Constant for the width of the window.
     */
    const val WINDOW_WIDTH = 400

    /**
     * Constant for the height of the window.
     */
    const val WINDOW_HEIGHT = 600

    /**
     * Window in which the installer is operating.
     */
    private lateinit var window: JFrame

    /**
     * Container in which the main content is drawn.
     */
    private lateinit var container: JPanel

    /**
     * Image that is used for the background wave.
     */
    private val background: Image = ImageIO.read(javaClass.getResource("/background.png"))

    /**
     * Current screen with paint() method that is called whenever the main container is repainted.
     */
    var screen: Screen = WelcomeScreen()

    /**
     * Screen that was shown before the current one.
     */
    private var previousScreen: Screen = screen

    /**
     * Transition that switches to the next screen.
     */
    private var screenSwitch = SmoothDoubleTransition.builder()
            .start(WINDOW_WIDTH.toDouble()).end(0.0)
            .fadeIn(ScreenIndexManager.FADE_IN).stay(ScreenIndexManager.STAY).fadeOut(ScreenIndexManager.FADE_OUT)
            .autoTransformator(ForwardNothing { screen != previousScreen }).build()

    /**
     * Called when starting the installer.
     */
    fun init()
    {
        window = JFrame()

        window.isResizable = false
        window.title = "Inception Cloud Minecraft Mod Installer"
        window.iconImage = ImageIO.read(javaClass.getResourceAsStream("/icon_32x.png"))
        window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        container = object : JPanel() // Override the paint() method in order to draw custom shapes
        {
            override fun paint(graphics: Graphics?)
            {
                graphics?.let {
                    callPaint(it)
                }
            }
        }

        container.addMouseListener(object : MouseAdapter()
        {
            override fun mouseClicked(e: MouseEvent?)
            {
                e?.let {
                    val otherEvent = MouseEvent(e.component, e.id, e.`when`, e.modifiers, e.x, e.y, e.clickCount, e.isPopupTrigger, e.button)
                    screen.mouseClicked(otherEvent)
                }
            }
        })

        container.addMouseMotionListener(object : MouseMotionAdapter()
        {
            override fun mouseMoved(e: MouseEvent?)
            {
                e?.let {
                    val otherEvent = MouseEvent(e.component, e.id, e.`when`, e.modifiers, e.x, e.y, e.clickCount, e.isPopupTrigger, e.button)
                    screen.mouseMoved(otherEvent)
                }
            }
        })

        container.preferredSize = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)

        window.add(container)
        window.pack()
        window.setLocationRelativeTo(null)
        window.isVisible = true

        registerFonts()

        Thread {
            while (true)
            {
                container.repaint()
                GraphicsLibrary.tickTransitions()
                Thread.sleep(10)
            }
        }.start()
    }

    /**
     * Calls the paint method on the current screen.
     */
    fun callPaint(graphics: Graphics)
    {
        val graphics2D = graphics as Graphics2D

        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
        graphics2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
        graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)

        graphics.color = Color(0xFFFFFF)
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT)

        if (!(screen.stepIndex == 0 && !WelcomeScreen.titleFlyIn.isAtEnd))
        {
            // Background
            graphics2D.drawImage(background, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null)

            // Title
            FontManager.drawCenteredString("Installation Wizard", WINDOW_WIDTH / 2, 55, 1, 36, graphics2D)
        }

        if (previousScreen != screen)
        {
            previousScreen.paint(graphics2D, -WINDOW_WIDTH + screenSwitch.castToInt(), 0, WINDOW_WIDTH, WINDOW_HEIGHT)
            screen.paint(graphics2D, screenSwitch.castToInt(), 0, WINDOW_WIDTH, WINDOW_HEIGHT)

            if (screenSwitch.isAtEnd)
            {
                previousScreen = screen
                screenSwitch = SmoothDoubleTransition.builder()
                        .start(WINDOW_WIDTH.toDouble()).end(0.0)
                        .fadeIn(ScreenIndexManager.FADE_IN).stay(ScreenIndexManager.STAY).fadeOut(ScreenIndexManager.FADE_OUT)
                        .autoTransformator(ForwardNothing { screen != previousScreen }).build()
            }
        } else
        {
            screen.paint(graphics2D, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT)
        }

        ScreenIndexManager.currentIndex = screen.stepIndex
        ScreenIndexManager.drawStepIndex(graphics2D)
    }
}