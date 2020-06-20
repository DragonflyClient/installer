package net.inceptioncloud.installer.backend.processes.preparing

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.backend.CustomError
import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.File

class UninstallingOldClient : InstallationProcess("Uninstalling old Client") {
    /**
     * Folder in which an old client would be installed.
     */
    private val folder = File("${InstallManager.MINECRAFT_PATH.absolutePath}\\versions\\ICMinecraftMod\\")

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
        if (!MinecraftModInstaller.occurredErrors.contains("fileDeletion/versions")) {
            status = if (folder.deleteRecursively()) 1 else (-1).also {
                MinecraftModInstaller.occurredErrors.add("fileDeletion/versions")
                CustomError("104", "File (${folder.absolutePath}) deletion failed").printStackTrace()
            }
        }
    }
}