package ru.iohin.songschords.feature.song.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.iohin.songschords.core.api.data.SongRepository
import ru.iohin.songschords.core.api.entity.Result
import javax.inject.Inject

class SongViewModel(
    private val songRepository: SongRepository,
    private val idlingResource: CountingIdlingResource
) : ViewModel() {
    private val _state = MutableStateFlow<SongState>(SongState.LoadingSongState)
    val state: StateFlow<SongState> = _state

    fun loadSong(id: Int) {
        viewModelScope.launch {
            idlingResource.increment()
            when (val result = songRepository.getSong(id)) {
                is Result.Success -> _state.value = SongState.SuccessSongState(Song.from(result.data))
                is Result.Error -> _state.value =
                    SongState.ErrorSongState(result.exception.message ?: "")

                is Result.Loading -> _state.value = SongState.LoadingSongState
            }
            idlingResource.decrement()
        }
    }

    class Factory @Inject constructor(
        private val songRepository: SongRepository,
        private val idlingResource: CountingIdlingResource
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SongViewModel::class.java)) {
                return SongViewModel(songRepository, idlingResource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}