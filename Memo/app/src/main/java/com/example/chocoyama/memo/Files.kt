package com.example.chocoyama.memo

import android.os.Environment
import android.text.format.DateFormat
import java.io.*
import java.util.*

fun getFiles() = getFilesDir().listFiles().toList()

fun outputFile(original: File?, content: String): File {
    val timeStamp = DateFormat.format("yyyy-MM-dd-hh-mm-ss", Date())

    val file = original ?: File(getFilesDir(), "meo-$timeStamp")

    val writer = BufferedWriter(FileWriter(file))
    writer.use {
        it.write(content)
        it.flush()
    }

    return file
}

fun inputFile(file: File): String {
    val reader = BufferedReader(FileReader(file))
    return reader.readLines().joinToString("\n")
}

private fun getFilesDir(): File {
    val publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

    if (publicDir != null) {
        if (!publicDir.exists()) publicDir.mkdir()
        return publicDir
    } else {
        val dir = File(Environment.getExternalStorageDirectory(), "MemoFiles")
        if (!dir.exists()) dir.mkdir()
        return dir
    }
}
