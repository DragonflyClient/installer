package net.inceptioncloud.installer.backend

import java.io.File
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.UIManager

class FileChooser(
    private val startDirectory: File,
    private val parent: JFrame?,
    private val buttonText: String,
    private val fileSelectionMode: Int,
    private val clickType: Int,
    private val style: String
) {

    fun start(): File? {
        UIManager.setLookAndFeel(style)

        val chooser = JFileChooser(startDirectory)
        chooser.fileSelectionMode = fileSelectionMode

        val choice = chooser.showDialog(parent, buttonText)
        if (choice == clickType) {
            return chooser.selectedFile
        }

        return null
    }

}