package ru.iohin.songschords.core.rest

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class RestResource<T>(
    val meta: Meta,
    val objects: List<T>
) {
    @Serializable
    data class Meta(
        val limit: Int,
        val next: String?,
        val offset: Int,
        val previous: String?,
        @SerialName("total_count")
        val totalCount: Int
    )
}
