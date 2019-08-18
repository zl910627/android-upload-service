package net.gotev.uploadservice.schemehandlers

import android.content.Context
import net.gotev.uploadservice.extensions.autoDetectMimeType
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * Handler for normal file paths, wraps java.io.File
 * @author stephentuso
 */
internal class FileSchemeHandler : SchemeHandler {
    private lateinit var file: File

    override fun init(path: String) {
        file = File(path)
    }

    override fun size(context: Context) = file.length()

    override fun stream(context: Context) = FileInputStream(file)

    override fun contentType(context: Context) = file.absolutePath.autoDetectMimeType()

    override fun name(context: Context) = file.name ?: throw IOException("Can't get file name for ${file.absolutePath}")
}
