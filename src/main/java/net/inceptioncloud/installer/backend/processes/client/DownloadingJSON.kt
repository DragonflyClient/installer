package net.inceptioncloud.installer.backend.processes.client

import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.File

class DownloadingJSON : InstallationProcess("Downloading JSON") {
    /**
     * Destination for the JAR file.
     */
    private val destination =
        File("${InstallManager.MINECRAFT_PATH.absolutePath}\\versions\\ICMinecraftMod\\ICMinecraftMod.json")

    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean = true

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute() {
        status =
            if (InstallManager.saveFile(destination, "${InstallManager.getVersionURL()}ICMinecraftMod.json")) 1 else -1
    }
}