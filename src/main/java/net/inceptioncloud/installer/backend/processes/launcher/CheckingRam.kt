package net.inceptioncloud.installer.backend.processes.launcher

import com.sun.management.OperatingSystemMXBean
import net.inceptioncloud.installer.backend.InstallationProcess
import java.lang.Exception
import java.lang.management.ManagementFactory
import kotlin.math.roundToInt

class CheckingRam : InstallationProcess("Checking RAM")
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

            val memorySize = (ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean).totalPhysicalMemorySize
            val gbytes = (memorySize / (1024 * 1024 * 1024F)).roundToInt()

            println("System has ${gbytes}gb of RAM")
            LauncherProfile.ram = gbytes

            1
        } catch (ex: Exception)
        {
            ex.printStackTrace()
            -1
        }
    }
}