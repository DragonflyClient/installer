package net.inceptioncloud.installer.backend.processes.preparing

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.backend.CustomError
import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.File

class CreatingAssetsFolder : InstallationProcess("Creating Assets Folder") {
    /**
     * Folder in which the old assets would be installed.
     */
    private val folder = File("${InstallManager.MINECRAFT_PATH.absolutePath}\\dragonfly\\")

    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean = true

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute() {
        if (!MinecraftModInstaller.occurredErrors.contains("fileCreation/inceptioncloud")) {
            status = if (folder.mkdirs()) 1 else (-1).also {
                MinecraftModInstaller.delayBeforeErrorScreen = true
                MinecraftModInstaller.occurredErrors.add("fileCreation/inceptioncloud")
                CustomError("102", "File (${folder.absolutePath}) creation failed").printStackTrace()
            }
        }
    }
}