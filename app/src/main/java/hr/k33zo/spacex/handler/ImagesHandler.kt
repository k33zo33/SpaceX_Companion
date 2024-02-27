package hr.k33zo.spacex.handler

import android.content.Context
import android.util.Log
import hr.k33zo.spacex.factory.createGetHttpUrlConnection
import java.io.File
import java.net.HttpURLConnection
import java.nio.file.Files
import java.nio.file.Paths

fun downloadImageAndStore(context: Context, mission_patch: String) : String?{

    val filename = mission_patch.substring(mission_patch.lastIndexOf(File.separatorChar)+1)
    val file: File = createFileManually(context, filename)

    try {
        val con: HttpURLConnection = createGetHttpUrlConnection(mission_patch)
        Files.copy(con.inputStream, Paths.get(file.toURI()))

        return file.absolutePath

    }catch (e: Exception){
        Log.e("IMAGES_HANDLER", e.toString(), e)
    }
    return null
}

fun createFileManually(context: Context, filename: String): File {
    val dir = context.applicationContext.getExternalFilesDir(null)
    val file = File(dir, filename)
    if (file.exists()) file.delete()

    return file
}
