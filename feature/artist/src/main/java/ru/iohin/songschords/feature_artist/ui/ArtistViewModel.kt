package ru.iohin.songschords.feature_artist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.iohin.songschords.core_api.data.SongRepository
import ru.iohin.songschords.core_api.entity.Result
import javax.inject.Inject

class ArtistViewModel(private val songRepository: SongRepository) : ViewModel() {
    private val _state: MutableStateFlow<ArtistState> =
        MutableStateFlow(ArtistState.LoadingArtistState)
    val state: StateFlow<ArtistState> = _state

    private var offset = 0
    private var canLoadMore = true

    fun loadSongs(artistId: Int) {
        _state.value = ArtistState.LoadingArtistState
        canLoadMore = true
        offset = 0
        loadSongs(artistId, offset)
    }

    fun loadMore(artistId: Int) {
        if (!canLoadMore) return
        loadSongs(artistId, offset)
    }

    private fun getCurrentArtistState() = when (val artist = _state.value) {
        is ArtistState.SuccessArtistState -> artist.result
        else -> Artist()
    }

    fun loadArtist(id: Int) {
        viewModelScope.launch {
            when (val result = songRepository.getArtist(id)) {
                is Result.Success -> {
                    _state.value = ArtistState.SuccessArtistState(Artist(
                        result.data.id,
                        result.data.name,
                        result.data.imageUrl,
                        result.data.description,
                        getCurrentArtistState().songs
                    ))
                }
                is Result.Error -> _state.value =
                    ArtistState.ErrorArtistState(result.exception.message ?: "")
                is Result.Loading -> _state.value = ArtistState.LoadingArtistState
            }
        }
    }

    private fun loadSongs(artistId: Int, offset: Int) {
        viewModelScope.launch {
            when (val result = songRepository.getSongs(artistId = artistId, offset = offset)) {
                is Result.Success -> {
                    val currentArtist = getCurrentArtistState()
                    val songs = currentArtist.songs.toMutableList()
                    songs.addAll(result.data.data.map {
                        Artist.Song(
                            it.id,
                            it.name
                        )
                    })
                    _state.value = ArtistState.SuccessArtistState(currentArtist.copy(
                        songs = songs
                    ))
                    canLoadMore = result.data.data.size == result.data.limit
                    this@ArtistViewModel.offset += result.data.data.size
                }
                is Result.Error -> _state.value =
                    ArtistState.ErrorArtistState(result.exception.message ?: "")
                is Result.Loading -> _state.value = ArtistState.LoadingArtistState
            }
        }
    }

    class Factory @Inject constructor(private val songRepository: SongRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistViewModel::class.java)) {
                return ArtistViewModel(songRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}