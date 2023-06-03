package ru.iohin.songschords.feature.song.ui

sealed class SongState {
    object LoadingSongState: SongState()
    data class ErrorSongState(val message: String): SongState()
    data class SuccessSongState(val song: Song): SongState()
}