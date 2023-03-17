package ru.iohin.songschords.core.data.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class RestSongFull(
    val artist: Int,
    val artistName: String,
    val author: String,
    val content: String,
    val copyright: String,
    @SerialName("create_date")
    @Serializable(DateSerializer::class)
    val createDate: Date, // "2023-03-16T12:34:57.883757",
    @SerialName("end_date")
    @Serializable(DateSerializer::class)
    val endDate: Date?,
    @SerialName("foreign_id")
    val foreignId: Int,
    val id: Int,
    val name: String,
    @SerialName("resource_uri")
    val resourceUri: String,
    @SerialName("update_date")
    @Serializable(DateSerializer::class)
    val updateDate: Date, // "2023-03-16T12:35:02.909227"
)
