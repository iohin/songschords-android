package ru.iohin.songschords.feature_search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.iohin.songschords.core_api.data.SongRepository
import ru.iohin.songschords.core_api.entity.Result
import javax.inject.Inject

class SearchViewModel(private val songRepository: SongRepository) : ViewModel() {
    private val _state: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.LoadingSearchState)
    val state: StateFlow<SearchState> = _state

    fun search(query: String) {
        viewModelScope.launch {
            when (val result = songRepository.getArtists()) {
                is Result.Success ->
                    _state.value = SearchState.SearchResultsState(result.data.data.map {
                        Artist(
                            it.name,
                            it.imageUrl
                        )
                    })
                is Result.Error ->
                    _state.value = SearchState.ErrorSearchState(result.exception.message ?: "")
                is Result.Loading -> _state.value = SearchState.LoadingSearchState
            }
        }
    }

    class Factory @Inject constructor(private val songRepository: SongRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                return SearchViewModel(songRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}