package net.inceptioncloud.installer.backend.processes.assets

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.backend.CustomError
import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.File

class DownloadingAssets : InstallationProcess("Downloading Assets") {
    /**
     * Destination for the assets folder.
     */
    private val destination = File("${InstallManager.MINECRAFT_PATH.absolutePath}\\dragonfly")

    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean = true

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute() {
        if (!destination.exists()) {
            destination.mkdir()
        }

        status = if (InstallManager.saveFile(
                File("$destination\\assets.zip"),
                "https://cdn.icnet.dev/dragonfly/assets.zip"
            )
        ) 1 else (-1).also {
            MinecraftModInstaller.delayBeforeErrorScreen = true
            CustomError(
                "301",
                "File on server (\"https://cdn.icnet.dev/dragonfly/assets.zip\") not found"
            ).printStackTrace()
        }
    }
}