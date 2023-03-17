package ru.iohin.songschords.core.data.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestSongHitRequest(
    @SerialName("song")
    val song: String
) {
    companion object {
        fun build(songId: Int) = RestSongHitRequest("${SongsChordsService.API_PATH}song/$songId/")
    }
}
