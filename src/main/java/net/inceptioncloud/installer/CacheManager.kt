package net.inceptioncloud.installer

import java.io.File

object CacheManager {
    private val tmpDir = File("${System.getProperty("java.io.tmpdir")}\\DragonflyInstaller")

    fun copyFile(file: File) {
        Logger.log("Caching file... (${file.absolutePath})")
        if (checkFolder()) {
            file.copyTo(File("$tmpDir\\${file.name}"), true)
            Logger.log("File caching successfull! (${file.absolutePath})")
        } else {
            Logger.log("File caching failed! (${file.absolutePath})")
        }
    }

    fun copyFolder(folder: File, name: String) {
        Logger.log("Caching folder... (${folder.absolutePath})")
        if (checkFolder()) {
            File("$tmpDir\\$name\\").mkdirs()
            for (file in folder.listFiles()) {
                if (file.isDirectory) {
                    for (file2 in file.listFiles()) {
                        file2.copyTo(File("$tmpDir\\$name\\${file.name}\\${file2.name}"), true)
                    }
                } else {
                    file.copyTo(File("$tmpDir\\$name\\${file.name}"), true)
                }
            }
            Logger.log("Folder caching successfull! (${folder.absolutePath})")
        } else {
            Logger.log("Folder caching failed! (${folder.absolutePath})")
        }
    }

    fun copyBack(target: String, folder: File) {
        if (checkFolder()) {
            val targetFolder = File("$tmpDir/$target/")

            for (file in targetFolder.listFiles()) {
                if (file.isDirectory) {
                    for (file2 in file.listFiles()) {
                        file2.copyTo(File("${folder.absolutePath}\\${file.name}\\${file2.name}"), true)
                    }
                } else {
                    file.copyTo(File("${folder.absolutePath}\\${file.name}"), true)
                }
            }
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