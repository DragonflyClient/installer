package net.inceptioncloud.installer.backend.processes.launcher

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.backend.CustomError
import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess

class ClosingLauncher : InstallationProcess("Closing Launcher") {
    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean = InstallManager.isProcessRunning("MinecraftLauncher.exe")

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute() {
        if (!MinecraftModInstaller.occurredErrors.contains("systemReading/launcher")) {
            status = if (InstallManager.killProcess("MinecraftLauncher.exe")) 1 else (-1).also {
                MinecraftModInstaller.occurredErrors.add("systemReading/launcher")
                CustomError("105", "Systemprocess (Minecraft Launcher) not accessible").printStackTrace()
            }
        }
    }
}