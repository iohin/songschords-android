package ru.iohin.songschords.feature.search.ui

sealed class SearchState {
    object LoadingSearchState : SearchState()
    data class SearchResultsState(val results: List<Artist>): SearchState()
    data class ErrorSearchState(val message: String): SearchState()
}
