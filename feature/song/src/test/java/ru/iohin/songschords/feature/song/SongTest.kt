package ru.iohin.songschords.feature.song

import org.junit.Assert.*
import org.junit.Test
import ru.iohin.songschords.feature.song.ui.Song

class SongTest {
    @Test
    fun `should parse chords from content`() {
        val expected = listOf("C", "D", "E")

        val content =
            "some [crd]C[/crd] word [crd]D[/crd] some [crd]C[/crd] word [crd]E[/crd] and [crd]D[/crd]"
        val actual = Song.getChords(content)

        assertEquals(expected, actual)
    }
}