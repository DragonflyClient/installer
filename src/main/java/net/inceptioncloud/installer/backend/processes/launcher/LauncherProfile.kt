package net.inceptioncloud.installer.backend.processes.launcher

import com.google.gson.JsonObject
import net.inceptioncloud.installer.backend.InstallManager
import java.io.File

object LauncherProfile
{
    /**
     * Amount of Random Access Memory in
     */
    var ram: Int = 0

    /**
     * JVM Arguments for instant respawn, etc...
     */
    lateinit var jvmArguments: String

    /**
     * Json Object that contains the current profile list.
     */
    lateinit var jsonObject: JsonObject

    /**
     * Destination for the JAR file.
     */
    val file = File("${InstallManager.MINECRAFT_PATH.absolutePath}\\launcher_profiles.json")

    //<editor-fold desc="Base-64 Image">
    const val imageBase64 =
        "data:image/png;base64,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"
    //</editor-fold>

}