package net.inceptioncloud.installer.backend.processes.preparing

import net.inceptioncloud.installer.backend.InstallManager
import net.inceptioncloud.installer.backend.InstallationProcess
import org.apache.commons.io.FileUtils
import java.io.File
import java.lang.Exception
import java.nio.charset.Charset

class StoringOptions : InstallationProcess("Storing Options")
{
    companion object
    {
        /**
         * Content of the options file.
         */
        var content: String? = null
    }

    /**
     * Folder in which the old assets would be installed.
     */
    private val target = File("${InstallManager.MINECRAFT_PATH.absolutePath}\\inceptioncloud\\options.json")

    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean
    {
        return target.exists()
    }

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute()
    {
        try {

            content = FileUtils.readFileToString(target, Charset.defaultCharset())
            status = if (content != null) 1 else 0
        } catch (ex: Exception)
        {
            status = -1
        }
    }
}