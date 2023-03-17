package ru.iohin.songschords.core.data.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
