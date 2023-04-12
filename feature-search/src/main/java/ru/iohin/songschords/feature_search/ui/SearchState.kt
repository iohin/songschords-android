package ru.iohin.songschords.feature_search.ui

import ru.iohin.songschords.core_api.entity.ArtistShort

sealed class SearchState {
    object LoadingSearchState : SearchState()
    data class SearchResultsState(val results: List<ArtistShort>): SearchState()
    data class ErrorSearchState(val message: String): SearchState()
}
