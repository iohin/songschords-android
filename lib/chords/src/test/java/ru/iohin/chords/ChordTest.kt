package ru.iohin.chords

import org.junit.Test
import org.junit.Assert.*

class ChordTest {
    @Test
    fun `should right base chords notes`() {
        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G), Chord("C").notes)
        assertArrayEquals(arrayOf(Notes.C, Notes.D_SHARP, Notes.G), Chord("Cm").notes)
        assertArrayEquals(arrayOf(Notes.C_SHARP, Notes.F, Notes.G_SHARP), Chord("C#").notes)
        assertArrayEquals(arrayOf(Notes.C_SHARP, Notes.E, Notes.G_SHARP), Chord("C#m").notes)
        assertArrayEquals(arrayOf(Notes.B, Notes.D_SHARP, Notes.F_SHARP), Chord("B").notes)
        assertArrayEquals(arrayOf(Notes.B, Notes.D, Notes.F_SHARP), Chord("Bm").notes)
    }

    @Test
    fun `should right seventh chords notes`() {
        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G, Notes.A_SHARP), Chord("C7").notes)
        assertArrayEquals(arrayOf(Notes.C, Notes.D_SHARP, Notes.G, Notes.A_SHARP), Chord("Cm7").notes)
        assertArrayEquals(arrayOf(Notes.C_SHARP, Notes.F, Notes.G_SHARP, Notes.B), Chord("C#7").notes)
        assertArrayEquals(arrayOf(Notes.C_SHARP, Notes.E, Notes.G_SHARP, Notes.B), Chord("C#m7").notes)
        assertArrayEquals(arrayOf(Notes.B, Notes.D_SHARP, Notes.F_SHARP, Notes.A), Chord("B7").notes)
        assertArrayEquals(arrayOf(Notes.B, Notes.D, Notes.F_SHARP, Notes.A), Chord("Bm7").notes)
    }
}