package ru.iohin.songschords.core.rest

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class RestSongHitRequest(
    @SerialName("song")
    val song: String
) {
    companion object {
        fun build(songId: Int) = RestSongHitRequest("${RestService.API_PATH}song/$songId/")
    }
}
