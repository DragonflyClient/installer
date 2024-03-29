package net.inceptioncloud.installer.backend.processes.launcher

import net.inceptioncloud.installer.Logger
import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import net.inceptioncloud.installer.backend.hardware.HardwareScore
import java.io.File
import java.io.FileReader

class AccessingJVMArguments : InstallationProcess("Accessing JVM Arguments") {
    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean = true

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute() {
        status = try {
            LauncherProfile.jvmArguments = FileReader(getArgumentsFile()).readText()
            Logger.log(LauncherProfile.jvmArguments)
            1
        } catch (ex: Exception) {
            (-1).also {
                MinecraftModInstaller.delayBeforeErrorScreen = true
                MinecraftModInstaller.reportError("103", "File (${getArgumentsFile().absolutePath}) not accessible")
            }
        }
    }

    /**
     * Returns the file with the recommended JVM arguments for the RAM-size-
     */
    private fun getArgumentsFile(): File {
        var i = 2

        LauncherProfile.score = HardwareScore.runTest()

        when {
            LauncherProfile.score <= 30000 -> {
                when {
                    LauncherProfile.ram >= 16 -> i = 16
                    LauncherProfile.ram >= 8 -> i = 6
                    LauncherProfile.ram >= 4 -> i = 3
                    LauncherProfile.ram >= 0 -> i = 2
                }
            }
            LauncherProfile.score > 30000 -> {
                when {
                    LauncherProfile.ram >= 16 -> i = 16
                    LauncherProfile.ram >= 8 -> i = 8
                    LauncherProfile.ram >= 4 -> i = 4
                    LauncherProfile.ram >= 0 -> i = 2
                }
            }
        }

        Logger.log("Using '${i}_gb.jvm' as jvm-preset file!")

        return File("${InstallManager.MINECRAFT_PATH}${File.separator}dragonfly${File.separator}assets${File.separator}jvm${File.separator}${i}_gb.jvm")
    }
}