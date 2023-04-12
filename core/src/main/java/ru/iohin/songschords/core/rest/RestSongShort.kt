package ru.iohin.songschords.core.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestSongShort(
    val artist: Int,
    val artistName: String,
    val id: Int,
    val name: String,
    @SerialName("resource_uri")
    val resourceUri: String,
)
