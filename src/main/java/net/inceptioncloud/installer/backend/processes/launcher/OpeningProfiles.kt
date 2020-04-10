package net.inceptioncloud.installer.backend.processes.launcher

import com.google.gson.JsonParser
import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.File
import java.io.FileReader

class OpeningProfiles : InstallationProcess("Opening Profiles")
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
            val jsonObject = JsonParser().parse(FileReader(LauncherProfile.file)).asJsonObject
            LauncherProfile.jsonObject = jsonObject
            1
        }
        catch (ex: Exception)
        {
            ex.printStackTrace()
            -1
        }
    }
}