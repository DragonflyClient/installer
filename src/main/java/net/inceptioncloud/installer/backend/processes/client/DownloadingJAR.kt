package net.inceptioncloud.installer.backend.processes.client

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.backend.CustomError
import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.File

class DownloadingJAR : InstallationProcess("Downloading JAR") {

    /**
     * Destination for the JAR file.
     */
    private val destination =
        File("${InstallManager.MINECRAFT_PATH.absolutePath}\\versions\\ICMinecraftMod\\ICMinecraftMod.jar")

    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean = true

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute() {
        if (!MinecraftModInstaller.occurredErrors.contains("url/jar")) {
            status = if (InstallManager.saveFile(
                    destination,
                    "${InstallManager.getVersionURL()}ICMinecraftMod.jar"
                )
            ) 1 else (-1).also {
                MinecraftModInstaller.occurredErrors.add("url/jar")
                CustomError(
                    "301",
                    "File on server (\"https://cdn.icnet.dev/minecraftmod/${InstallManager.getVersionURL()}ICMinecraftMod.jar\") not found"
                ).printStackTrace()
            }
        }
    }
}