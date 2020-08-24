package net.inceptioncloud.installer.backend.hardware.components

import com.sun.management.OperatingSystemMXBean
import java.lang.management.ManagementFactory

object RAM {

    fun getSize(): Int {
        val memorySize =
            (ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean).totalPhysicalMemorySize
        val mbytes = (memorySize / (1024 * 1024))

        return mbytes.toInt()
    }

}