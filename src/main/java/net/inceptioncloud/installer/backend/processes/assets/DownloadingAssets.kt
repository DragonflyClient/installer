package net.inceptioncloud.installer.backend.processes.assets

import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.File

class DownloadingAssets : InstallationProcess("Downloading Assets")
{
    /**
     * Destination for the JAR file.
     */
    private val destination = File("${InstallManager.MINECRAFT_PATH.absolutePath}\\inceptioncloud\\")

    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean = true

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute()
    {
        status = if(InstallManager.saveFolder(destination, "https://cdn.icnet.dev/minecraftmod/assets/")) 1 else -1
    }
}