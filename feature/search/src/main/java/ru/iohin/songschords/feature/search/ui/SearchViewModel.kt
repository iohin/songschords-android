package ru.iohin.songschords.feature.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.iohin.songschords.core.api.data.SongRepository
import ru.iohin.songschords.core.api.entity.Result
import javax.inject.Inject

class SearchViewModel(private val songRepository: SongRepository) : ViewModel() {
    private val _state: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.LoadingSearchState)
    val state: StateFlow<SearchState> = _state

    private var currentQuery = ""
    private var offset = 0
    private var canLoadMore = true

    init {
        search("")
    }

    fun search(query: String) {
        _state.value = SearchState.LoadingSearchState
        canLoadMore = true
        currentQuery = query
        offset = 0
        search(query, offset)
    }

    fun loadMore() {
        if (!canLoadMore) return
        search(currentQuery, offset)
    }

    private fun search(query: String, offset: Int) {
        viewModelScope.launch {
            when (val result = songRepository.getArtists(query, offset)) {
                is Result.Success -> {
                    val state = _state.value
                    val artists = if (state is SearchState.SearchResultsState) {
                        state.results.toMutableList()
                    } else {
                        mutableListOf()
                    }
                    artists.addAll(result.data.data.map {
                        Artist(
                            it.id,
                            it.name,
                            it.imageUrl
                        )
                    })
                    _state.value = SearchState.SearchResultsState(artists)
                    canLoadMore = result.data.data.size == result.data.limit
                    this@SearchViewModel.offset += result.data.data.size
                }
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