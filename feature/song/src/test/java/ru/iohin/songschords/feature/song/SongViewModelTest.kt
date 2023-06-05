package ru.iohin.songschords.feature.song

import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*
import ru.iohin.songschords.core.api.data.SongRepository
import ru.iohin.songschords.core.api.entity.Result
import ru.iohin.songschords.core.api.entity.SongFull
import ru.iohin.songschords.feature.song.ui.Song
import ru.iohin.songschords.feature.song.ui.SongState
import ru.iohin.songschords.feature.song.ui.SongViewModel
import ru.iohin.songschords.testlib.MainDispatcherRule

class SongViewModelTest {
    private val songRepository: SongRepository = mock()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `should load song`() = runTest {
        val song = SongFull(
            id = 1,
            name = "song 1",
            artistId = 1,
            artistName = "artist 1",
            author = "author 1",
            content = "content 1",
            copyright = "copyright 1",
        )
        wheneverBlocking { songRepository.getSong(any()) }.thenReturn(Result.Success(song))

        val songViewModel = SongViewModel(songRepository)
        songViewModel.loadSong(1)

        val expected = SongState.SuccessSongState(Song.of(song))
        val actual = songViewModel.state.value

        assertEquals(expected, actual)
    }

    @Test
    fun `should error on load song`() = runTest {
        wheneverBlocking { songRepository.getSong(any()) }.thenReturn(Result.Error(Error("error")))

        val songViewModel = SongViewModel(songRepository)
        songViewModel.loadSong(1)

        val expected = SongState.ErrorSongState("error")
        val actual = songViewModel.state.value

        assertEquals(expected, actual)
    }
}