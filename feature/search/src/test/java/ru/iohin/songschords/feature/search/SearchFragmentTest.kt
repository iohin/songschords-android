package ru.iohin.songschords.feature.search

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.*
import org.robolectric.shadows.ShadowToast
import ru.iohin.songschords.feature.search.di.SearchFragmentComponent
import ru.iohin.songschords.feature.search.ui.Artist
import ru.iohin.songschords.feature.search.ui.ArtistsAdapter
import ru.iohin.songschords.feature.search.ui.SearchFragment
import ru.iohin.songschords.feature.search.ui.SearchState
import ru.iohin.songschords.feature.search.ui.SearchViewModel


@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {
    private val searchViewModel: SearchViewModel = mock()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `should display error`() = runTest {
        val flow = MutableStateFlow<SearchState>(SearchState.LoadingSearchState)
        whenever(searchViewModel.state).thenReturn(flow)

        val factoryMock = mock<SearchViewModel.Factory>()
        whenever(factoryMock.create(eq(SearchViewModel::class.java))).thenReturn(searchViewModel)
        whenever(factoryMock.create(eq(SearchViewModel::class.java), any())).thenReturn(searchViewModel)

        val fragmentScenario = launchFragmentInContainer<SearchFragment>(initialState = Lifecycle.State.INITIALIZED)
        fragmentScenario.onFragment { fragment ->
            mockkObject(SearchFragmentComponent.Companion)
            every {
                SearchFragmentComponent.Companion.getSearchFragmentComponent(fragment)
            } returns mock()
            fragment.viewModelFactory = factoryMock
        }
        fragmentScenario.moveToState(Lifecycle.State.STARTED)

        flow.emit(SearchState.ErrorSearchState("error"))

        assertEquals("error", ShadowToast.getTextOfLatestToast())
    }

    @Test
    fun `should display artists`() = runTest {
        val flow = MutableStateFlow<SearchState>(SearchState.LoadingSearchState)
        whenever(searchViewModel.state).thenReturn(flow)

        val factoryMock = mock<SearchViewModel.Factory>()
        whenever(factoryMock.create(eq(SearchViewModel::class.java))).thenReturn(searchViewModel)
        whenever(factoryMock.create(eq(SearchViewModel::class.java), any())).thenReturn(searchViewModel)

        val fragmentScenario = launchFragmentInContainer<SearchFragment>(initialState = Lifecycle.State.INITIALIZED)
        fragmentScenario.onFragment { fragment ->
            mockkObject(SearchFragmentComponent.Companion)
            every {
                SearchFragmentComponent.Companion.getSearchFragmentComponent(fragment)
            } returns mock()
            fragment.viewModelFactory = factoryMock
        }
        fragmentScenario.moveToState(Lifecycle.State.STARTED)

        val expected = listOf(
            Artist(1, "artist 1", null)
        )
        flow.emit(SearchState.SearchResultsState(expected))

        fragmentScenario.onFragment { fragment ->
            val recyclerView: RecyclerView? = fragment.view?.findViewById(R.id.recycler_view)
            val actual = (recyclerView?.adapter as ArtistsAdapter).artists
            assertEquals(expected, actual)
        }
    }
}