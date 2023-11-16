package com.andy.rios.elektra.ui.util

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import androidx.recyclerview.widget.DiffUtil
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DiffCallback<K>(val compareItems: (old: K, new: K) -> Boolean, val compareContents: (old: K, new: K) -> Boolean) : DiffUtil.ItemCallback<K>() {
    override fun areItemsTheSame(old: K, new: K) = compareItems(old, new)
    override fun areContentsTheSame(old: K, new: K) = compareContents(old, new)
}

fun crearCopiaDeArchivo(context: Context, uriFile: Uri?): File? {
    var inputStream: FileInputStream? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        // Open a specific media item using ParcelFileDescriptor.
        // "rw" for read-and-write;
        // "rwt" for truncating or overwriting existing file contents.
        val readOnlyMode = "r"
        val parcelFileDescriptor: ParcelFileDescriptor?
        try {
            parcelFileDescriptor =
                context.contentResolver.openFileDescriptor(uriFile!!, readOnlyMode, null)
        } catch (e: FileNotFoundException) {
            return null
        }
        if (parcelFileDescriptor != null) {
            inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        }
    } else {
        val file = File(uriFile!!.path.toString())
        inputStream = FileInputStream(file)
    }
    val nombreArchivo = "FILE-" +
            SimpleDateFormat(
                "yyyyMMdd_HHmmss",
                Locale.getDefault()
            ).format(Date()) + "." + context.contentResolver.getType(uriFile)!!.split("/")[1]
    val fileImagenProcesada = File(context.cacheDir, nombreArchivo)
    if (inputStream != null) {
        val outputStream = FileOutputStream(fileImagenProcesada)
        inputStream.close()
        outputStream.close()
    }
    return fileImagenProcesada
}
