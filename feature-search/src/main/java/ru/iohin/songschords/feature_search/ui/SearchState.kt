package ru.iohin.songschords.feature_search.ui

sealed class SearchState {
    object LoadingSearchState : SearchState()
    data class SearchResultsState(val results: List<Artist>): SearchState()
    data class ErrorSearchState(val message: String): SearchState()
}
