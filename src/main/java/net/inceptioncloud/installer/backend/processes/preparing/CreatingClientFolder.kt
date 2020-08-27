package net.inceptioncloud.installer.backend.processes.preparing

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.File

class CreatingClientFolder : InstallationProcess("Creating Client Folder") {
    /**
     * Folder in which an old client would be installed.
     */
    private val folder =
        File("${InstallManager.MINECRAFT_PATH.absolutePath}${File.separator}versions${File.separator}Dragonfly-1.8.8${File.separator}")

    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean = true

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute() {
        status = if (folder.mkdirs()) 1 else (-1).also {
            MinecraftModInstaller.delayBeforeErrorScreen = true
            MinecraftModInstaller.reportError("102", "File (${folder.absolutePath}) creation failed")
        }
    }
}