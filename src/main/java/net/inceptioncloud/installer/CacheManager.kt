package net.inceptioncloud.installer

import java.io.File

object CacheManager {
    private val tmpDir = File("${System.getProperty("java.io.tmpdir")}/DragonflyInstaller")

    fun copyFile(file: File): Boolean {
        return if (checkFolder()) {
            file.copyTo(File("$tmpDir/${file.name}"), true)
            true
        } else {
            false
        }
    }

    fun copyFolder(folder: File): Boolean {
        return if (checkFolder()) {
            File("$tmpDir/${folder.name}/").mkdir()
            for (file in folder.listFiles()) {
                file.copyTo(File("$tmpDir/${folder.name}/${file.name}"), true)
            }
            true
        } else {
            false
        }

    }

    fun deleteFolder() {
        tmpDir.deleteRecursively()
    }

    private fun checkFolder(): Boolean {
        if (!tmpDir.exists()) {
            return tmpDir.mkdirs()
        } else {
            return true
        }
    }
}