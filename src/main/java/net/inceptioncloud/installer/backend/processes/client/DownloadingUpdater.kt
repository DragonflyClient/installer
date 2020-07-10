package net.inceptioncloud.installer.backend.processes.client

import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.backend.CustomError
import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.File

class DownloadingUpdater : InstallationProcess("Downloading Updater") {

    /**
     * Destination for the Updater JAR file.
     */
    private val updaterDestination =
        File("${System.getenv("appdata")}\\Dragonfly\\Dragonfly-Updater.jar")

    /**
     * Destination for the Update Scheduler JAR file.
     */
    private val schedulerDestination =
        File("${System.getenv("appdata")}\\Dragonfly\\Dragonfly-Updater-Scheduler.jar")

    override fun test(): Boolean = true

    override fun execute() {
        if (!MinecraftModInstaller.occurredErrors.contains("url/updater")) {
            status = if (InstallManager.saveFile(
                    updaterDestination,
                    "https://cdn.icnet.dev/dragonfly/updater/Dragonfly-Updater.jar"
                ) && InstallManager.saveFile(
                    schedulerDestination,
                    "https://cdn.icnet.dev/dragonfly/updater/Dragonfly-Updater-Scheduler.jar"
                )
            ) 1 else (-1).also {
                MinecraftModInstaller.delayBeforeErrorScreen = true
                MinecraftModInstaller.occurredErrors.add("url/updater")
                CustomError(
                    "301",
                    "File on server (\"https://cdn.icnet.dev/dragonfly/updater/Dragonfly-Updater.jar\") not found"
                ).printStackTrace()
            }
        }
    }

}