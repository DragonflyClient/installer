package net.inceptioncloud.installer.backend.processes.launcher

import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.File

class ClosingLauncher : InstallationProcess("Closing Launcher")
{
    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean = InstallManager.isProcessRunning("MinecraftLauncher.exe")

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute()
    {
        status = if (InstallManager.killProcess("MinecraftLauncher.exe")) 1 else -1
    }
}