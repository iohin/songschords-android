package ru.iohin.songschords.feature.song

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.*
import org.robolectric.shadows.ShadowToast
import ru.iohin.songschords.feature.song.di.SongFragmentComponent
import ru.iohin.songschords.feature.song.ui.Song
import ru.iohin.songschords.feature.song.ui.SongFragment
import ru.iohin.songschords.feature.song.ui.SongState
import ru.iohin.songschords.feature.song.ui.SongViewModel
import ru.iohin.songschords.testlib.MainDispatcherRule

@RunWith(AndroidJUnit4::class)
class SongFragmentTest {
    private val songViewModel: SongViewModel = mock()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `should display error`() = runTest {
        val flow = MutableStateFlow<SongState>(SongState.LoadingSongState)
        whenever(songViewModel.state).thenReturn(flow)

        val factoryMock = mock<SongViewModel.Factory>()
        whenever(factoryMock.create(eq(SongViewModel::class.java))).thenReturn(songViewModel)
        whenever(factoryMock.create(eq(SongViewModel::class.java), any())).thenReturn(songViewModel)

        val fragmentScenario =
            launchFragmentInContainer<SongFragment>(
                initialState = Lifecycle.State.INITIALIZED,
                fragmentArgs = Bundle().apply {
                    putInt("id", 1)
                    putString("name", "song 1")
                    putString("artistName", "artist 1")
                }
            )
        fragmentScenario.onFragment { fragment ->
            mockkObject(SongFragmentComponent.Companion)
            every { SongFragmentComponent.getSongFragmentComponent(fragment) } returns mock()
            fragment.viewModelFactory = factoryMock
        }

        fragmentScenario.moveToState(Lifecycle.State.STARTED)

        flow.emit(SongState.ErrorSongState("error"))

        Assert.assertEquals("error", ShadowToast.getTextOfLatestToast())
    }

    @Test
    fun `should display song`() = runTest {
        val flow = MutableStateFlow<SongState>(SongState.LoadingSongState)
        whenever(songViewModel.state).thenReturn(flow)

        val factoryMock = mock<SongViewModel.Factory>()
        whenever(factoryMock.create(eq(SongViewModel::class.java))).thenReturn(songViewModel)
        whenever(factoryMock.create(eq(SongViewModel::class.java), any())).thenReturn(songViewModel)

        val fragmentScenario =
            launchFragmentInContainer<SongFragment>(
                initialState = Lifecycle.State.INITIALIZED,
                fragmentArgs = Bundle().apply {
                    putInt("id", 1)
                    putString("name", "song 1")
                    putString("artistName", "artist 1")
                }
            )
        fragmentScenario.onFragment { fragment ->
            mockkObject(SongFragmentComponent.Companion)
            every { SongFragmentComponent.getSongFragmentComponent(fragment) } returns mock()
            fragment.viewModelFactory = factoryMock
        }

        fragmentScenario.moveToState(Lifecycle.State.STARTED)

        flow.emit(SongState.SuccessSongState(Song(
            "song 1",
            "artist 1",
            "content 1"
        )))

        Espresso.onView(ViewMatchers.withId(R.id.song_name))
            .check(matches(ViewMatchers.withText("song 1")))
        Espresso.onView(ViewMatchers.withId(R.id.artist_name))
            .check(matches(ViewMatchers.withText("artist 1")))
        Espresso.onView(ViewMatchers.withId(R.id.content))
            .check(matches(ViewMatchers.withText("content 1")))
    }
}