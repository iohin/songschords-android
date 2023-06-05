package ru.iohin.songschords.feature.search

import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*
import ru.iohin.songschords.core.api.data.SongRepository
import ru.iohin.songschords.core.api.entity.ArtistShort
import ru.iohin.songschords.core.api.entity.Resource
import ru.iohin.songschords.core.api.entity.Result
import ru.iohin.songschords.feature.search.ui.Artist
import ru.iohin.songschords.feature.search.ui.SearchState
import ru.iohin.songschords.feature.search.ui.SearchViewModel
import ru.iohin.songschords.testlib.MainDispatcherRule

class SearchViewModelTest {
    private val songRepository: SongRepository = mock()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `should load artists`() = runTest {
        wheneverBlocking {
            songRepository.getArtists(any(), eq(0), any())
        }.thenReturn(
            Result.Success(
                Resource(
                    1, 1, 0, listOf(
                        ArtistShort(1, "song 1", "imageUrl")
                    )
                )
            )
        )
        wheneverBlocking {
            songRepository.getArtists(any(), eq(1), any())
        }.thenReturn(
            Result.Success(
                Resource(
                    1, 1, 1, listOf(
                        ArtistShort(2, "song 2", "imageUrl2")
                    )
                )
            )
        )

        val searchViewModel = SearchViewModel(songRepository)
        searchViewModel.loadMore()

        val expected = SearchState.SearchResultsState(
            listOf(
                Artist(1, "song 1", "imageUrl"),
                Artist(2, "song 2", "imageUrl2")
            )
        )
        val actual = searchViewModel.state.value

        assertEquals(expected, actual)
    }

    @Test
    fun `should error on load artists`() = runTest {
        wheneverBlocking {
            songRepository.getArtists(any(), eq(0), any())
        }.thenReturn(
            Result.Error(Error("error"))
        )

        val searchViewModel = SearchViewModel(songRepository)

        val expected = SearchState.ErrorSearchState("error")
        val actual = searchViewModel.state.value

        assertEquals(expected, actual)
    }
}