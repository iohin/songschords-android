package ru.iohin.songschords.feature.artist.ui

sealed class ArtistState {
    object LoadingArtistState : ArtistState()
    data class SuccessArtistState(val result: Artist): ArtistState()
    data class ErrorArtistState(val message: String): ArtistState()
}