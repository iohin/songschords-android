package ru.iohin.songschords.feature.search.ui

data class SearchData(
    val results: List<Artist> = emptyList(),
    val suggestions: List<String> = emptyList()
)
