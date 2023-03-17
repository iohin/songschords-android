package ru.iohin.songschords.core.data.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class RestArtistFull(
    @SerialName("create_date")
    @Serializable(DateSerializer::class)
    val createВate: Date?, //"2023-03-16T12:34:57.073404",
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
