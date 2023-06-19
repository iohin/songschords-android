package ru.iohin.chords

import org.junit.Assert.*
import org.junit.Test

class NotesTest {
    @Test
    fun `should right notes by chord steps`() {
        assertEquals("C", Notes.chordStep("C", Mode.MAJOR, 1))
        assertEquals("D", Notes.chordStep("C", Mode.MAJOR, 2))
        assertEquals("E", Notes.chordStep("C", Mode.MAJOR, 3))
        assertEquals("F", Notes.chordStep("C", Mode.MAJOR, 4))
        assertEquals("G", Notes.chordStep("C", Mode.MAJOR, 5))
        assertEquals("A", Notes.chordStep("C", Mode.MAJOR, 6))
        assertEquals("B", Notes.chordStep("C", Mode.MAJOR, 7))
        assertEquals("C", Notes.chordStep("C", Mode.MAJOR, 8))

        assertEquals("C", Notes.chordStep("C", Mode.MINOR, 1))
        assertEquals("D", Notes.chordStep("C", Mode.MINOR, 2))
        assertEquals("D#", Notes.chordStep("C", Mode.MINOR, 3))
        assertEquals("F", Notes.chordStep("C", Mode.MINOR, 4))
        assertEquals("G", Notes.chordStep("C", Mode.MINOR, 5))
        assertEquals("G#", Notes.chordStep("C", Mode.MINOR, 6))
        assertEquals("A#", Notes.chordStep("C", Mode.MINOR, 7))

        assertEquals("C#", Notes.chordStep("C#", Mode.MAJOR, 1))
        assertEquals("D#", Notes.chordStep("C#", Mode.MAJOR, 2))
        assertEquals("F", Notes.chordStep("C#", Mode.MAJOR, 3))
        assertEquals("F#", Notes.chordStep("C#", Mode.MAJOR, 4))
        assertEquals("G#", Notes.chordStep("C#", Mode.MAJOR, 5))
        assertEquals("A#", Notes.chordStep("C#", Mode.MAJOR, 6))
        assertEquals("C", Notes.chordStep("C#", Mode.MAJOR, 7))

        assertEquals("C#", Notes.chordStep("C#", Mode.MINOR, 1))
        assertEquals("D#", Notes.chordStep("C#", Mode.MINOR, 2))
        assertEquals("E", Notes.chordStep("C#", Mode.MINOR, 3))
        assertEquals("F#", Notes.chordStep("C#", Mode.MINOR, 4))
        assertEquals("G#", Notes.chordStep("C#", Mode.MINOR, 5))
        assertEquals("A", Notes.chordStep("C#", Mode.MINOR, 6))
        assertEquals("B", Notes.chordStep("C#", Mode.MINOR, 7))

        assertEquals("B", Notes.chordStep("B", Mode.MAJOR, 1))
        assertEquals("C#", Notes.chordStep("B", Mode.MAJOR, 2))
        assertEquals("D#", Notes.chordStep("B", Mode.MAJOR, 3))
        assertEquals("E", Notes.chordStep("B", Mode.MAJOR, 4))
        assertEquals("F#", Notes.chordStep("B", Mode.MAJOR, 5))
        assertEquals("G#", Notes.chordStep("B", Mode.MAJOR, 6))
        assertEquals("A#", Notes.chordStep("B", Mode.MAJOR, 7))

        assertEquals("B", Notes.chordStep("B", Mode.MINOR, 1))
        assertEquals("C#", Notes.chordStep("B", Mode.MINOR, 2))
        assertEquals("D", Notes.chordStep("B", Mode.MINOR, 3))
        assertEquals("E", Notes.chordStep("B", Mode.MINOR, 4))
        assertEquals("F#", Notes.chordStep("B", Mode.MINOR, 5))
        assertEquals("G", Notes.chordStep("B", Mode.MINOR, 6))
        assertEquals("A", Notes.chordStep("B", Mode.MINOR, 7))
    }

    @Test
    fun `should right before notes`() {
        assertEquals("B", Notes.before("C"))
        assertEquals("C", Notes.before("C#"))
        assertEquals("C#", Notes.before("D"))
        assertEquals("D", Notes.before("D#"))
        assertEquals("D#", Notes.before("E"))
        assertEquals("E", Notes.before("F"))
        assertEquals("F", Notes.before("F#"))
        assertEquals("F#", Notes.before("G"))
        assertEquals("G", Notes.before("G#"))
        assertEquals("G#", Notes.before("A"))
        assertEquals("A", Notes.before("A#"))
        assertEquals("A#", Notes.before("B"))
    }

    @Test
    fun `should right after notes`() {
        assertEquals("C#", Notes.after("C"))
        assertEquals("D", Notes.after("C#"))
        assertEquals("D#", Notes.after("D"))
        assertEquals("E", Notes.after("D#"))
        assertEquals("F", Notes.after("E"))
        assertEquals("F#", Notes.after("F"))
        assertEquals("G", Notes.after("F#"))
        assertEquals("G#", Notes.after("G"))
        assertEquals("A", Notes.after("G#"))
        assertEquals("A#", Notes.after("A"))
        assertEquals("B", Notes.after("A#"))
        assertEquals("C", Notes.after("B"))
    }
}