package net.inceptioncloud.installer

import net.inceptioncloud.installer.backend.CustomError
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
        val fileName = "${SimpleDateFormat("HH_mm_ss-dd_MM_yyyy").format(System.currentTimeMillis())}.log"
        file = File(fileName)

        if (!file.createNewFile()) {
            fileCreationFailed = true
            CustomError("001", "Creation of log file (${file.absolutePath}) failed!").printStackTrace()
        } else {
            file.appendText("This is log started at: ${SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(System.currentTimeMillis())}\n")
        }
    }

    /**
     * This is an internal function which is used to write the text into the .log file of the current logger session
     */
    private fun writeToFile(text: String) = file.appendText("\n$text")

}