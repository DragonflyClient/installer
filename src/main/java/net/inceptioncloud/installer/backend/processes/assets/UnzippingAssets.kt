package net.inceptioncloud.installer.backend.processes.assets

import net.inceptioncloud.installer.Logger
import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import net.inceptioncloud.installer.backend.processes.launcher.LauncherProfile
import net.lingala.zip4j.ZipFile
import java.io.File

class UnzippingAssets : InstallationProcess("Unzipping Assets") {

    /**
     * Destination for the assets folder.
     */
    private val destination =
        File("${InstallManager.MINECRAFT_PATH.absolutePath}${File.separator}dragonfly${File.separator}")

    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean = true

    override fun execute() {
        status = if (unzip(File("${destination}${File.separator}assets.zip"))
        ) 1 else (-1).also {
            MinecraftModInstaller.delayBeforeErrorScreen = true
            MinecraftModInstaller.reportError("102", "File (${LauncherProfile.file.absolutePath}) creation failed")
        }
    }

    fun unzip(file: File): Boolean {
        try {
            Logger.log("Unzipping assets...")

            val zipFile = ZipFile(file)
            zipFile.extractAll(destination.absolutePath)

            Logger.log("Deleting assets-zip...")
            file.deleteRecursively()

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

}