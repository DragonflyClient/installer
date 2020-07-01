package net.inceptioncloud.installer.frontend.screens

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.MinecraftModInstaller.WINDOW_HEIGHT
import net.inceptioncloud.installer.frontend.FontManager
import net.inceptioncloud.installer.frontend.Screen
import net.inceptioncloud.installer.frontend.objects.UIButton
import net.inceptioncloud.installer.frontend.objects.UIImage
import net.inceptioncloud.installer.frontend.transition.number.SmoothDoubleTransition
import net.inceptioncloud.installer.frontend.transition.supplier.AlwaysForward
import net.inceptioncloud.installer.frontend.transition.supplier.ForwardNothing
import java.awt.Color
import java.awt.Desktop
import java.awt.Graphics2D
import java.awt.Image
import java.awt.event.MouseEvent
import java.net.URI
import javax.imageio.ImageIO

/**
 * First Screen that is shown when opening the installer.
 */
class WelcomeScreen : Screen(0) {

    /**
     * Image that is used for the background wave.
     */
    private val background: Image = ImageIO.read(javaClass.getResource("/background.png"))

    /**
     * Button that is clicked to start the setup.
     */
    private val button: UIButton = object : UIButton("Let's go!") {
        override fun buttonClicked() {
            MinecraftModInstaller.screen = EarlyAccessScreen()
        }
    }

    /**
     * Social Media Icon for Twitter
     */
    private val twitter = object : UIImage("/social_media/twitter.png") {
        override fun buttonClicked() {
            Desktop.getDesktop().browse(URI("https://icnet.dev/twitter"))
        }
    }

    /**
     * Social Media Icon for Instagram
     */
    private val instagram =
        object : UIImage("/social_media/instagram.png") {
            override fun buttonClicked() {
                Desktop.getDesktop().browse(URI("https://icnet.dev/insta"))
            }
        }

    /**
     * Social Media Icon for Discord
     */
    private val discord = object : UIImage("/social_media/discord.png") {
        override fun buttonClicked() {
            Desktop.getDesktop().browse(URI("https://icnet.dev/discord"))
        }
    }

    /**
     * Transition for the title to fly in.
     */
    private val contentFlyIn = SmoothDoubleTransition.builder()
        .start(WINDOW_HEIGHT / 1.5).end(0.0)
        .fadeIn(0).stay(25).fadeOut(25)
        .autoTransformator(ForwardNothing { titleFlyIn.isAtEnd }).build()

    companion object {
        /**
         * Transition for the title to fly in.
         */
        val titleFlyIn: SmoothDoubleTransition = SmoothDoubleTransition.builder()
            .start(-WINDOW_HEIGHT.toDouble() / 6).end(0.0)
            .fadeIn(25).stay(25).fadeOut(25)
            .autoTransformator(AlwaysForward()).build()

        /**
         * Transition for the background to fly in.
         */
        private val backgroundFlyIn = SmoothDoubleTransition.builder()
            .start(-WINDOW_HEIGHT.toDouble() / 6).end(0.0)
            .fadeIn(0).stay(30).fadeOut(30)
            .autoTransformator(AlwaysForward()).build()

        /**
         * Transition for the button to fly in.
         */
        val buttonFlyIn: SmoothDoubleTransition = SmoothDoubleTransition.builder()
            .start(WINDOW_HEIGHT.toDouble() / 6).end(0.0)
            .fadeIn(0).stay(30).fadeOut(30)
            .autoTransformator(AlwaysForward()).build()
    }

    init {
        childs.add(button)
        childs.add(twitter)
        childs.add(instagram)
        childs.add(discord)
    }

    /**
     * Called when painting the screen.
     */
    override fun paint(graphics2D: Graphics2D, x: Int, y: Int, width: Int, height: Int) {

        if (!titleFlyIn.isAtEnd) {
            // Background
            graphics2D.drawImage(background, x + 0, y + backgroundFlyIn.castToInt(), width, height, null)

            // Title
            FontManager.drawCenteredString(
                "Installation Wizard",
                x + width / 2,
                y + 55 + titleFlyIn.castToInt(),
                1,
                36,
                graphics2D
            )
        }

        // Paragraph
        graphics2D.color = Color(50, 50, 50)
        FontManager.drawCenteredString("Welcome", x + width / 2 + contentFlyIn.castToInt(), y + 160, 0, 30, graphics2D)
        FontManager.drawCenteredString(
            "to the Inception Cloud Dragonfly",
            x + width / 2 + contentFlyIn.castToInt(),
            y + 195,
            2,
            24,
            graphics2D
        )
        FontManager.drawCenteredString(
            "Mod Installation Wizard!",
            x + width / 2 + contentFlyIn.castToInt(),
            y + 225,
            2,
            24,
            graphics2D
        )
        FontManager.drawCenteredString(
            "Press the button below to",
            x + width / 2 + contentFlyIn.castToInt(),
            y + 270,
            2,
            24,
            graphics2D
        )
        FontManager.drawCenteredString(
            "download and install the",
            x + width / 2 + contentFlyIn.castToInt(),
            y + 300,
            2,
            24,
            graphics2D
        )
        FontManager.drawCenteredString(
            "Modification.",
            x + width / 2 + contentFlyIn.castToInt(),
            y + 330,
            2,
            24,
            graphics2D
        )

        // Social Media
        val iconY = y + 360
        val iconWidth = 37
        twitter.paint(
            graphics2D,
            (x + (width / 2) - iconWidth * 1.5 - 20 + contentFlyIn.castToInt()).toInt(),
            iconY,
            iconWidth,
            iconWidth
        )
        instagram.paint(
            graphics2D,
            x + (width / 2) - iconWidth / 2 + contentFlyIn.castToInt(),
            iconY,
            iconWidth,
            iconWidth
        )
        discord.paint(
            graphics2D,
            x + (width / 2) + iconWidth / 2 + 20 + contentFlyIn.castToInt(),
            iconY,
            iconWidth,
            iconWidth
        )

        // Button
        val buttonWidth = (width / 2)
        val buttonHeight = buttonWidth / 5.1
        button.paint(
            graphics2D,
            x + width / 2 - buttonWidth / 2,
            y + 500 + buttonFlyIn.castToInt(),
            buttonWidth,
            buttonHeight.toInt()
        )
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