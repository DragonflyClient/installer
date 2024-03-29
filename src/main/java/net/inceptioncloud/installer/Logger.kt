package net.inceptioncloud.installer

import java.io.File
import java.text.SimpleDateFormat

object Logger {

    /**
     * File of the current logger session
     */
    private lateinit var file: File

    /**
     * Boolean to stop the logger in case of the file-creation failed
     */
    private var fileCreationFailed = false

    /**
     * Function which is used to print new outputs
     * In the console and as well in the .log file of the current logger session
     */
    fun log(text: String) {
        val prefix = "[${SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis())}] "

        if (!fileCreationFailed) {
            writeToFile(prefix + text)
        }

        println(prefix + text)
    }

    /**
     * This function has to be called before the first log
     * It is used to create the .log file of the current logger session
     */
    fun createFile() {
        val folder = File("dragonfly-installer-logs${File.separator}")

        try {
            if (!folder.exists()) {
                folder.mkdir()
            }
        } catch (e: Exception) {
            fileCreationFailed = true
            MinecraftModInstaller.reportError("001", "Creation of log folder (${folder.absolutePath}) failed")
        }

        val fileName =
            "dragonfly-installer-logs${File.separator}${SimpleDateFormat("HH_mm_ss-dd_MM_yyyy").format(System.currentTimeMillis())}.log"
        file = File(fileName)

        if (!file.createNewFile()) {
            fileCreationFailed = true
            MinecraftModInstaller.reportError("001", "Creation of log file (${file.absolutePath}) failed")
        } else {
            file.appendText("This is log started at: ${SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(System.currentTimeMillis())}\n")
        }
    }

    /**
     * This is an internal function which is used to write the text into the .log file of the current logger session
     */
    private fun writeToFile(text: String) = file.appendText("\n$text")

}