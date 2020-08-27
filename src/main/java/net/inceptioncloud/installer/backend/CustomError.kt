package net.inceptioncloud.installer.backend

import net.inceptioncloud.installer.CacheManager
import net.inceptioncloud.installer.Logger
import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.frontend.screens.ErrorScreen
import java.io.File
import java.util.*

/**
 *
 *  0XX: Warnings
 *
 *  1XX: Client-backend
 *
 *  2XX: Client-frontend
 *
 *  3XX: Server-side
 *
 */
class CustomError(private val errorCode: String, private val errorString: String) {

    fun printStackTrace() {

        var spacer = "--"
        for (char in errorString) {
            spacer += "-"
        }

        Logger.log(spacer)
        Logger.log(" Error $errorCode")
        Logger.log(" $errorString")
        Logger.log(spacer)

        if (!errorCode.startsWith("0")) {

            /* Version restoring */
            if (MinecraftModInstaller.restoreOldVersion) {
                Logger.log("Started version restoring!")
                Logger.log("Restoring version...")
                CacheManager.copyBack(
                    "client",
                    File("${InstallManager.MINECRAFT_PATH.absolutePath}${File.separator}versions${File.separator}Dragonfly-1.8.8${File.separator}")
                )
                Logger.log("Restoring assets...")
                CacheManager.copyBack(
                    "assets",
                    File("${InstallManager.MINECRAFT_PATH.absolutePath}${File.separator}dragonfly${File.separator}assets${File.separator}")
                )
                Logger.log("Version restoring successfull!")
                MinecraftModInstaller.restoredOldVersion = true
            }

            /* Screen switching */
            if (MinecraftModInstaller.delayBeforeErrorScreen) {
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        MinecraftModInstaller.screen = ErrorScreen(errorCode.toInt())
                    }
                }, 900)
            } else {
                MinecraftModInstaller.screen = ErrorScreen(errorCode.toInt())
            }
        }


        throw Exception("An custom error occurred!")
    }

}