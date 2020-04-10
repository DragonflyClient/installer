package net.inceptioncloud.installer.backend.processes.launcher

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import net.inceptioncloud.installer.backend.InstallationProcess
import java.io.FileWriter
import java.text.SimpleDateFormat

class AddingProfile : InstallationProcess("Adding Profile")
{
    /**
     * Called when showing the corresponding screen in order to check if the process is required.
     */
    override fun test(): Boolean = true

    /**
     * Executes the download / installation that the process is responsible for.
     */
    override fun execute()
    {
        status = try
        {
            val jsonObject = LauncherProfile.jsonObject
            val profiles = jsonObject["profiles"].asJsonObject
            val entries = HashSet(profiles.entrySet())
            profiles.entrySet().clear()

            val customProfile = JsonObject()
            customProfile.addProperty("created", SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(System.currentTimeMillis()))
            customProfile.addProperty("lastUsed", SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(System.currentTimeMillis()))
            customProfile.addProperty("lastVersionId", "ICMinecraftMod")
            customProfile.addProperty("name", "IC Minecraft Mod")
            customProfile.addProperty("type", "custom")
            customProfile.addProperty("javaArgs", LauncherProfile.jvmArguments)
            customProfile.addProperty("icon", LauncherProfile.imageBase64)

            profiles.add("ICMinecraftMod", customProfile)

            entries.filter { it.key != "ICMinecraftMod" }
                .forEach {
                    profiles.add(it.key, it.value)
                }

            val gson = GsonBuilder().setPrettyPrinting().create()
            val fileWriter = FileWriter(LauncherProfile.file)

            fileWriter.write(gson.toJson(jsonObject))
            fileWriter.flush()
            fileWriter.close()

            1
        }
        catch (ex: Exception)
        {
            ex.printStackTrace()
            -1
        }
    }
}