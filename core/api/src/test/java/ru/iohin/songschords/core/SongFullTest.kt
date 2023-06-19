package ru.iohin.songschords.core

import org.junit.Assert.*
import org.junit.Test
import ru.iohin.songschords.core.api.entity.SongFull

class SongFullTest {
    @Test
    fun `should parse chords from content`() {
        val expected = listOf("C", "D", "E")

        val song = SongFull(
            0,
            "",
            0,
            "",
            "",
            "some [crd]C[/crd] word [crd]D[/crd] some [crd]C[/crd] word [crd]E[/crd] and [crd]D[/crd]",
            ""
        )
        val actual = song.getChords()

        assertEquals(expected, actual)
    }
}