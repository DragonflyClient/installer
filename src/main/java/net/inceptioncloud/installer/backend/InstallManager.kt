package net.inceptioncloud.installer.backend


import net.inceptioncloud.installer.Logger
import net.inceptioncloud.installer.MinecraftModInstaller
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

/**
 * Manages important variables and functions for the Installing Process.
 */
object InstallManager {
    /**
     * Path to the .minecraft/ folder.
     */
    var MINECRAFT_PATH = File("${System.getenv("APPDATA")}\\.minecraft\\")

    /**
     * Saves a file from a web server to the local machine.
     */
    fun saveFile(file: File, urlString: String): Boolean {
        val splitTemp = urlString.replace("https://", "").split("/")
        if (!MinecraftModInstaller.occurredErrors.contains("url/${splitTemp[splitTemp.size - 1]}")) {
            Logger.log("Saving $urlString to file $file")
            var `in`: InputStream? = null
            var out: FileOutputStream? = null

            try {
                if (!MinecraftModInstaller.occurredErrors.contains("versionURL")) {

                    val con: HttpURLConnection = URL(urlString).openConnection() as HttpURLConnection
                    `in` = con.inputStream
                    out = FileOutputStream(file)
                    val data = ByteArray(1024)
                    var count: Int
                    while (`in`.read(data, 0, 1024).also { count = it } != -1) {
                        out.write(data, 0, count)
                    }

                    return true
                }
            } catch (e: Exception) {
                MinecraftModInstaller.occurredErrors.add("url/${splitTemp[splitTemp.size - 1]}")
                CustomError(
                    "301",
                    "File on server (\"$urlString\") not found"
                ).printStackTrace()
                return false
            } finally {
                `in`?.close()
                out?.close()
            }
        }
        return false
    }

    /**
     * Saves a folder from a web server to the local machine.
     */
    fun saveFolder(folder: File, urlString: String): Boolean {
        val splitTemp = urlString.replace("https://", "").split("/")
        if (!MinecraftModInstaller.occurredErrors.contains("url/${splitTemp[splitTemp.size - 1]}")) {
            Logger.log("Saving $urlString to folder $folder")

            try {
                val doc: Document = Jsoup.connect(urlString).get()
                val links: Elements = doc.getElementsByTag("a")
                var failed = false

                for (link in links) {
                    val target = link.attr("href").toString()

                    if (target.startsWith("?") || target.startsWith("/dragonfly/"))
                        continue

                    val remoteTarget = "$urlString$target"

                    if (target.endsWith("/")) {
                        val localFolderTarget = "$folder\\$target\\"
                        if (!File(localFolderTarget).mkdirs() || !saveFolder(File(localFolderTarget), remoteTarget))
                            failed = true
                    } else if (!saveFile(File("$folder\\$target".replace("%20", " ")), remoteTarget)) {
                        failed = true
                    }
                }

                return !failed
            } catch (ex: IOException) {
                MinecraftModInstaller.occurredErrors.add("url/${splitTemp[splitTemp.size - 1]}")
                CustomError(
                    "301",
                    "File on server (\"$urlString\") not found"
                ).printStackTrace()
                return false
            }
        }
        return false
    }

    fun getVersionURL(): String {
        var result = "https://cdn.icnet.dev/dragonfly/"

        if (!MinecraftModInstaller.occurredErrors.contains("url/?-version")) {
            try {
                result += if (MinecraftModInstaller.downloadEAP) {
                    khttp.get(
                        url = "https://api.inceptioncloud.net/version?channel=stable"
                    ).jsonObject.get("version").toString()
                } else {
                    khttp.get(
                        url = "https://api.inceptioncloud.net/version?channel=stable"
                    ).jsonObject.get("version").toString()
                }
            } catch (e: Exception) {
                MinecraftModInstaller.occurredErrors.add("url/?-version")
                CustomError(
                    "301",
                    "File on server (\"$result\") not found"
                ).printStackTrace()
            }
        }

        return "$result/"
    }

    /**
     * Checks if a certain process is running on the windows machine.
     */
    fun isProcessRunning(processName: String): Boolean {
        var line = ""
        var pidInfo = ""
        val process = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe")
        val input = BufferedReader(InputStreamReader(process.inputStream))

        while (input.readLine().also { if (it != null) line = it } != null) {
            pidInfo += line
        }

        input.close()

        return pidInfo.contains(processName)
    }

    /**
     * Kills a certain process on the window.
     */
    fun killProcess(processName: String): Boolean {
        return try {
            Runtime.getRuntime().exec("taskkill /F /IM $processName")
            true
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            false
        }
    }
}