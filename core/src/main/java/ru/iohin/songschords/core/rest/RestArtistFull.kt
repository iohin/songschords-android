package ru.iohin.songschords.core.rest

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import java.util.Date

@Serializable
data class RestArtistFull(
    @SerialName("create_date")
    @Serializable(DateSerializer::class)
    val createDate: Date?,
    val description: String,
    @SerialName("end_date")
    @Serializable(DateSerializer::class)
    val endDate: Date?,
    @SerialName("foreign_id")
    val foreignId: Int,
    val id: Int,
    @SerialName("image_url")
    val imageUrl: String?,
    val name: String,
    @SerialName("name_aliases")
    val nameAliases: String,
    @SerialName("resource_uri")
    val resourceUri: String,
    @SerialName("update_date")
    @Serializable(DateSerializer::class)
    val updateDate: Date //"2023-03-16T12:43:13.568896"
)
