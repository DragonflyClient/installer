package net.inceptioncloud.installer.frontend

import net.inceptioncloud.installer.CacheManager
import net.inceptioncloud.installer.Logger

object ShutdownHook : Thread() {

    override fun run() {
        CacheManager.deleteFolder()
        Logger.log("Closing wizard...")
    }

}