package net.inceptioncloud.installer

import net.inceptioncloud.installer.backend.CustomError
import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.frontend.FontManager
import net.inceptioncloud.installer.frontend.FontManager.registerFonts
import net.inceptioncloud.installer.frontend.Screen
import net.inceptioncloud.installer.frontend.ScreenIndexManager
import net.inceptioncloud.installer.frontend.ShutdownHook
import net.inceptioncloud.installer.frontend.screens.ErrorScreen
import net.inceptioncloud.installer.frontend.screens.UpdateScreen
import net.inceptioncloud.installer.frontend.screens.WelcomeScreen
import net.inceptioncloud.installer.frontend.transition.Transition
import net.inceptioncloud.installer.frontend.transition.number.SmoothDoubleTransition
import net.inceptioncloud.installer.frontend.transition.supplier.ForwardNothing
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * Core of the Minecraft Mod Installer.
 */
object MinecraftModInstaller {

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
    lateinit var window: JFrame

    /**
     * Container in which the main content is drawn.
     */
    private lateinit var container: JPanel

    /**
     * Current screen with paint() method that is called whenever the main container is repainted.
     */
    var screen: Screen? = null
        set(value) {
            if (value is ErrorScreen) {
                if (occurredErrors.size == 1) {
                    field = value
                }
            } else if (occurredErrors.size == 0) {
                field = value
            }
        }

    var occurredErrors = mutableListOf<String>()

    var version = "1.2.3"

    /**
     * A list with all transitions.
     */
    val transitions = mutableListOf<Transition>()

    /**
     * Screen that was shown before the current one.
     */
    private lateinit var previousScreen: Screen

    /**
     * Transition that switches to the next screen.
     */
    lateinit var screenSwitch: SmoothDoubleTransition

    /**
     * Boolean to store if an delay occurs before the switching to the error screen
     */
    var delayBeforeErrorScreen = false

    /**
     * Boolean to store if the old client version needs to be restored
     */
    var restoreOldVersion = true

    /**
     * Boolean to store if the old client version was restored
     */
    var restoredOldVersion = false

    /**
     * Boolean to store if the fix tab is already open in the browser
     */
    var tabOpen = false

    lateinit var OS: String

    /**
     * Called when starting the installer.
     */
    fun init() {
        Logger.createFile()

        OS = System.getProperty("os.name")

        screen = WelcomeScreen()
        previousScreen = screen!!

        screenSwitch = SmoothDoubleTransition.builder()
            .start(WINDOW_WIDTH.toDouble()).end(0.0)
            .fadeIn(ScreenIndexManager.FADE_IN).stay(ScreenIndexManager.STAY).fadeOut(ScreenIndexManager.FADE_OUT)
            .autoTransformator(ForwardNothing { screen != previousScreen }).build()

        window = JFrame()

        window.isResizable = false
        window.title = "Dragonfly Mod Installer"

        try {
            window.iconImage = ImageIO.read(javaClass.getResourceAsStream("/icon_32x.png"))
        } catch (e: Exception) {
            reportError("201", "Image (resources/icon32x.png) not found")
        }

        window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        container = object : JPanel() // Override the paint() method in order to draw custom shapes
        {
            override fun paint(graphics: Graphics?) {
                graphics?.let {
                    callPaint(it)
                }
            }
        }

        container.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                e?.let {
                    val otherEvent = MouseEvent(
                        e.component,
                        e.id,
                        e.`when`,
                        e.modifiers,
                        e.x,
                        e.y,
                        e.clickCount,
                        e.isPopupTrigger,
                        e.button
                    )
                    screen!!.mouseClicked(otherEvent)
                }
            }
        })

        container.addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseMoved(e: MouseEvent?) {
                e?.let {
                    val otherEvent = MouseEvent(
                        e.component,
                        e.id,
                        e.`when`,
                        e.modifiers,
                        e.x,
                        e.y,
                        e.clickCount,
                        e.isPopupTrigger,
                        e.button
                    )
                    screen!!.mouseMoved(otherEvent)
                }
            }
        })

        container.preferredSize = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
        Runtime.getRuntime().addShutdownHook(ShutdownHook)

        window.add(container)
        window.pack()
        window.setLocationRelativeTo(null)
        window.isVisible = true

        registerFonts()

        Thread {
            while (true) {
                container.repaint()
                ArrayList(transitions).forEach { it.tick() }
                Thread.sleep(10)
            }
        }.start()

        if (!InstallManager.hasInternetConnection()) {
            reportError("106", "No internet connection")
        }

        checkNewestVersion()

    }

    /**
     * Var with boolean which is used to store if the user has selected the eap-version for the downloads
     */
    var downloadEAP = false

    /**
     * Calls the paint method on the current screen.
     */
    fun callPaint(graphics: Graphics) {
        val graphics2D = graphics as Graphics2D

        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
        graphics2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
        graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)

        graphics.color = Color(0xFFFFFF)
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT)

        if (!(screen!!.stepIndex == 0 && !WelcomeScreen.titleFlyIn.isAtEnd)) {

            try {
                // Background
                graphics2D.drawImage(
                    ImageIO.read(javaClass.getResource("/background.png")),
                    0,
                    0,
                    WINDOW_WIDTH,
                    WINDOW_HEIGHT,
                    null
                )
            } catch (e: Exception) {
                reportError("201", "Image (resources/background.png) not found")
            }

            // Title
            FontManager.drawCenteredString("Installation Wizard", WINDOW_WIDTH / 2, 55, 1, 36, graphics2D)
        }

        if (previousScreen != screen) {
            previousScreen.paint(graphics2D, -WINDOW_WIDTH + screenSwitch.castToInt(), 0, WINDOW_WIDTH, WINDOW_HEIGHT)
            screen!!.paint(graphics2D, screenSwitch.castToInt(), 0, WINDOW_WIDTH, WINDOW_HEIGHT)

            if (screenSwitch.isAtEnd) {
                previousScreen = screen!!

                screenSwitch = SmoothDoubleTransition.builder()
                    .start(WINDOW_WIDTH.toDouble()).end(0.0)
                    .fadeIn(ScreenIndexManager.FADE_IN).stay(ScreenIndexManager.STAY)
                    .fadeOut(ScreenIndexManager.FADE_OUT)
                    .autoTransformator(ForwardNothing { screen != previousScreen }).build()
            }
        } else {
            screen!!.paint(graphics2D, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT)
        }

        ScreenIndexManager.currentIndex = screen!!.stepIndex
        ScreenIndexManager.drawStepIndex(graphics2D)

    }

    fun reportError(errorCode: String, errorString: String) {
        if (!occurredErrors.contains(errorCode)) {
            occurredErrors.add(errorCode)
            CustomError(errorCode, errorString).printStackTrace()
        }
    }

    private fun checkNewestVersion() {
        val newest = khttp.get(
            "https://api.inceptioncloud.net/version/installer"
        ).jsonObject.get("version").toString()

        val versionI = version.replace(".", "").toInt().addZerosTo(3)
        val newestI = newest.replace(".", "").toInt().addZerosTo(3)

        if (versionI < newestI) {
            Logger.log("Installer version is outdated. Update required! ($version -> $newest)")
            screen = UpdateScreen(newest)
        } else {
            Logger.log("Installer is up to date. ($version - $newest)")
        }
    }

    private fun Int.addZerosTo(amount: Int): Int {
        var string = this.toString()

        while (string.length != amount) {
            string += "0"
        }

        return string.toInt()
    }

}