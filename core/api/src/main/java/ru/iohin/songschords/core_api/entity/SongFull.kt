package ru.iohin.songschords.core_api.entity

data class SongFull(
    val id: Int,
    val name: String,
    val artistId: Int,
    val artistName: String,
    val author: String,
    val content: String,
    val copyright: String,
)
