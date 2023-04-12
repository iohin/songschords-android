package ru.iohin.songschords.core.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestArtistShort(
    val id: Int,
    val name: String,
    @SerialName("image_url")
    val imageUrl: String?,
    @SerialName("name_aliases")
    val nameAliases: String,
    @SerialName("resource_uri")
    val resourceUri: String,
)
