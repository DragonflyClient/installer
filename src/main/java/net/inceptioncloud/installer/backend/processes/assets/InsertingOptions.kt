package net.inceptioncloud.installer.backend.processes.assets

import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import net.inceptioncloud.installer.backend.processes.preparing.StoringOptions
import java.io.File
import java.io.FileWriter
import java.lang.Exception

class InsertingOptions : InstallationProcess("Inserting Options")
{
    /**
     * Folder in which the old assets would be installed.
     */
    private val target = File("${InstallManager.MINECRAFT_PATH.absolutePath}\\inceptioncloud\\options.json")

    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean = StoringOptions.content != null

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute()
    {
        status = try {
            target.createNewFile()

            val writer = FileWriter(target)
            writer.write(StoringOptions.content!!)
            writer.flush()
            writer.close()
            1
        } catch (ex: Exception)
        {
            -1
        }
    }
}