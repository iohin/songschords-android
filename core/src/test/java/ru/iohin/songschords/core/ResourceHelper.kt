package ru.iohin.songschords.core

import java.io.File

object ResourceHelper {
    fun getJson(path: String): String {
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}