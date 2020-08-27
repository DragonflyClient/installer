package net.inceptioncloud.installer.backend.processes.client

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.File

class DownloadingUpdater : InstallationProcess("Downloading Updater") {

    /**
     * Destination for the Updater JAR file.
     */
    private val updaterDestination =
        File("${getFolder()}Dragonfly${File.separator}Dragonfly-Updater.jar")

    /**
     * Destination for the Update Scheduler JAR file.
     */
    private val schedulerDestination =
        File("${getFolder()}Dragonfly${File.separator}Dragonfly-Updater-Scheduler.jar")

    override fun test(): Boolean = true

    override fun execute() {
        status = if (InstallManager.saveFile(
                updaterDestination,
                "https://cdn.icnet.dev/dragonfly/updater/Dragonfly-Updater.jar"
            ) && InstallManager.saveFile(
                schedulerDestination,
                "https://cdn.icnet.dev/dragonfly/updater/Dragonfly-Updater-Scheduler.jar"
            )
        ) 1 else (-1).also {
            MinecraftModInstaller.delayBeforeErrorScreen = true
            MinecraftModInstaller.reportError(
                "301",
                "File on server (\"https://cdn.icnet.dev/dragonfly/updater/Dragonfly-Updater.jar\") not found"
            )
        }
    }

    private fun getFolder(): String {
        when {
            MinecraftModInstaller.OS.toLowerCase().contains("windows") -> {
                return System.getenv("appdata")
            }
            MinecraftModInstaller.OS.toLowerCase().contains("linux") -> {
                return System.getProperty("user.home") + File.separator
            }
            MinecraftModInstaller.OS.toLowerCase().contains("os") -> {
                return "/Users/" + System.getProperty("user.name") + "/Library/Application Support/"
            }
        }
        return "ERROR"
    }

}