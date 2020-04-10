package net.inceptioncloud.installer.backend.processes.launcher

import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.File
import java.io.FileReader

class AccessingJVMArguments : InstallationProcess("Accessing JVM Arguments")
{
    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean = true

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute()
    {
        status = try
        {
            LauncherProfile.jvmArguments = FileReader(getArgumentsFile()).readText()
            println(LauncherProfile.jvmArguments)
            1
        }
        catch (ex: Exception)
        {
            ex.printStackTrace()
            -1
        }
    }

    /**
     * Returns the file with the recommended JVM arguments for the RAM-size-
     */
    private fun getArgumentsFile(): File
    {
        var i = 2

        when
        {
            LauncherProfile.ram >= 16 -> i = 16
            LauncherProfile.ram >= 8 -> i = 8
            LauncherProfile.ram >= 4 -> i = 4
            LauncherProfile.ram >= 0 -> i = 2
        }

        return File("${InstallManager.MINECRAFT_PATH}\\inceptioncloud\\jvm\\${i}_gb.jvm")
    }
}