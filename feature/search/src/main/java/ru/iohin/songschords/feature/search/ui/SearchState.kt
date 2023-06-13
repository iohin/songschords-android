package ru.iohin.songschords.feature.search.ui

sealed class SearchState {
    object LoadingSearchState : SearchState()
    data class SearchResultsState(val searchData: SearchData): SearchState()
    data class ErrorSearchState(val message: String): SearchState()

    fun clone(searchData: SearchData): SearchState {
        return if (this is SearchResultsState) {
            SearchResultsState(searchData)
        } else {
            return this
        }
    }
}
