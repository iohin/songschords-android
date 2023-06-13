package ru.iohin.songschords.feature.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.iohin.songschords.core.api.data.SongRepository
import ru.iohin.songschords.core.api.entity.Result
import javax.inject.Inject

class SearchViewModel(
    private val songRepository: SongRepository,
    private val idlingResource: CountingIdlingResource
) : ViewModel() {
    private val _state: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.LoadingSearchState)
    val state: StateFlow<SearchState> = _state

    private var currentQuery = ""
    private var offset = 0
    private var canLoadMore = true

    init {
        search("")
    }

    private fun getCurrentSearchState() = when (val state = _state.value) {
        is SearchState.SearchResultsState -> state.searchData
        else -> SearchData()
    }

    fun loadSuggestions(query: String) {
        if (query.isEmpty()) {
            _state.value = SearchState.SearchResultsState(
                SearchData(
                    getCurrentSearchState().results,
                    emptyList()
                )
            )
            return
        }
        viewModelScope.launch {
            when (val result = songRepository.getArtists(query)) {
                is Result.Success -> _state.value = SearchState.SearchResultsState(
                    SearchData(
                        getCurrentSearchState().results,
                        result.data.data.map { it.name }
                    )
                )
                else -> _state.value = SearchState.SearchResultsState(
                    SearchData(
                        getCurrentSearchState().results,
                        emptyList()
                    )
                )
            }
        }
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
            idlingResource.increment()
            when (val result = songRepository.getArtists(query, offset)) {
                is Result.Success -> {
                    val artists = getCurrentSearchState().results.toMutableList()
                    artists.addAll(result.data.data.map { Artist.from(it) })
                    _state.value = SearchState.SearchResultsState(SearchData(
                        artists,
                        getCurrentSearchState().suggestions
                    ))
                    canLoadMore = result.data.data.size == result.data.limit
                    this@SearchViewModel.offset += result.data.data.size
                }
                is Result.Error ->
                    _state.value = SearchState.ErrorSearchState(result.exception.message ?: "")
                is Result.Loading -> _state.value = SearchState.LoadingSearchState
            }
            idlingResource.decrement()
        }
    }

    class Factory @Inject constructor(
        private val songRepository: SongRepository,
        private val idlingResource: CountingIdlingResource
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                return SearchViewModel(songRepository, idlingResource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}