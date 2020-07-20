package net.inceptioncloud.installer.backend.processes.launcher

import com.google.gson.JsonParser
import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.FileReader

class OpeningProfiles : InstallationProcess("Opening Profiles") {
    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean = true

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute() {
        status = try {
            val jsonObject = JsonParser().parse(FileReader(LauncherProfile.file)).asJsonObject
            LauncherProfile.jsonObject = jsonObject
            1
        } catch (ex: Exception) {
            (-1).also {
                MinecraftModInstaller.delayBeforeErrorScreen = true
                MinecraftModInstaller.reportError("103", "File (${LauncherProfile.file.absolutePath}) not accessible")
            }
        }
    }
}