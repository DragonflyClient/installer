package net.inceptioncloud.installer.backend

import net.inceptioncloud.installer.Logger
import net.inceptioncloud.installer.MinecraftModInstaller
import net.inceptioncloud.installer.frontend.screens.ErrorScreen

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
            MinecraftModInstaller.screen = ErrorScreen(errorCode.toInt())
        }

    }

}