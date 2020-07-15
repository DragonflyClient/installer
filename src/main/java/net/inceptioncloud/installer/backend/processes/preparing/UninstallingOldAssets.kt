package net.inceptioncloud.installer.backend.processes.preparing

import net.inceptioncloud.installer.CacheManager
import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.backend.CustomError
import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.File

class UninstallingOldAssets : InstallationProcess("Uninstalling old Assets") {
    /**
     * Folder in which the old assets would be installed.
     */
    private val folder = File("${InstallManager.MINECRAFT_PATH.absolutePath}\\dragonfly\\assets\\")

    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean {
        return folder.exists()
    }

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute() {
        CacheManager.copyFolder(folder, "assets")
        status = if (folder.deleteRecursively()) 1 else (-1).also {
            MinecraftModInstaller.delayBeforeErrorScreen = true
            CustomError("104", "File (${folder.absolutePath}) deletion failed").printStackTrace()
        }
    }
}