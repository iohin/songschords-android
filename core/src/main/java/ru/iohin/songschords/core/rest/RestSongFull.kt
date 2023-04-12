package ru.iohin.songschords.core.rest

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
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
    val createDate: Date,
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
    val updateDate: Date,
)
