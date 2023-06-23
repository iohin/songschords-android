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
    fun `should right augmented chords notes`() {
        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G_SHARP), Chord("C+").notes)
        assertArrayEquals(arrayOf(Notes.C, Notes.D_SHARP, Notes.G_SHARP), Chord("Cm+").notes)
    }

    @Test
    fun `should right fifth dim chords notes`() {
        // todo
//        assertArrayEquals(arrayOf(Notes.C, Notes.F_SHARP), Chord("C5-").notes)
//        assertArrayEquals(arrayOf(Notes.C, Notes.F_SHARP), Chord("Cm5-").notes)
    }

    @Test
    fun `should right seventh fifth aug chords notes`() {
        // todo
//        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G, Notes.A_SHARP, Notes.G_SHARP), Chord("C75+").notes)
    }

    @Test
    fun `should right fourth chords notes`() {
        assertArrayEquals(arrayOf(Notes.C, Notes.F), Chord("C4").notes)
        // todo
//        assertArrayEquals(arrayOf(Notes.C, Notes.F), Chord("Cm4").notes)
    }

    @Test
    fun `should right fifth chords notes`() {
        assertArrayEquals(arrayOf(Notes.C, Notes.G), Chord("C5").notes)
    }

    @Test
    fun `should right sixth chords notes`() {
        val C6 = Chord("C6")
        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G), C6.notes)
        assertArrayEquals(arrayOf(Notes.A), C6.additionalNotes)
        val Cm6 = Chord("Cm6")
        assertArrayEquals(arrayOf(Notes.C, Notes.D_SHARP, Notes.G), Cm6.notes)
        assertArrayEquals(arrayOf(Notes.G_SHARP), Cm6.additionalNotes)
    }

    @Test
    fun `should right seventh chords notes`() {
        val C7 = Chord("C7")
        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G), C7.notes)
        assertArrayEquals(arrayOf(Notes.A_SHARP), C7.additionalNotes)
        val Cm7 = Chord("Cm7")
        assertArrayEquals(arrayOf(Notes.C, Notes.D_SHARP, Notes.G), Cm7.notes)
        assertArrayEquals(arrayOf(Notes.A_SHARP), Cm7.additionalNotes)
    }

    @Test
    fun `should right seventh + chords notes`() {
        val Cm7plus = Chord("Cm7+")
        assertArrayEquals(arrayOf(Notes.C, Notes.D_SHARP, Notes.G), Cm7plus.notes)
        assertArrayEquals(arrayOf(Notes.B), Cm7plus.additionalNotes)
    }

    @Test
    fun `should right ninth chords notes`() {
        val C9 = Chord("C9")
        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G), C9.notes)
        assertArrayEquals(arrayOf(Notes.A_SHARP, Notes.D), C9.additionalNotes)
        val Cm9 = Chord("Cm9")
        assertArrayEquals(arrayOf(Notes.C, Notes.D_SHARP, Notes.G), Cm9.notes)
        assertArrayEquals(arrayOf(Notes.A_SHARP, Notes.D), Cm9.additionalNotes)
    }

    @Test
    fun `should right maj chords`() {
        val Cmaj = Chord("Cmaj")
        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G), Cmaj.notes)
        assertArrayEquals(arrayOf(Notes.B), Cmaj.additionalNotes)
    }

    @Test
    fun `should right maj6 chords`() {
        val Cmaj6 = Chord("Cmaj6")
        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G), Cmaj6.notes)
        assertArrayEquals(arrayOf(Notes.A), Cmaj6.additionalNotes)
    }

    @Test
    fun `should right maj9 chords`() {
        val Cmaj9 = Chord("Cmaj9")
        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G), Cmaj9.notes)
        assertArrayEquals(arrayOf(Notes.B, Notes.D), Cmaj9.additionalNotes)
    }

    @Test
    fun `should right add 9 chords`() {
        val Cadd9 = Chord("Cadd9")
        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G), Cadd9.notes)
        assertArrayEquals(arrayOf(Notes.D), Cadd9.additionalNotes)
        val Cmadd9 = Chord("Cmadd9")
        assertArrayEquals(arrayOf(Notes.C, Notes.D_SHARP, Notes.G), Cmadd9.notes)
        assertArrayEquals(arrayOf(Notes.D), Cmadd9.additionalNotes)
    }

    @Test
    fun `should right add 11 chords`() {
        val Cadd11 = Chord("Cadd11")
        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G), Cadd11.notes)
        assertArrayEquals(arrayOf(Notes.F), Cadd11.additionalNotes)
        val Cmadd11 = Chord("Cmadd11")
        assertArrayEquals(arrayOf(Notes.C, Notes.D_SHARP, Notes.G), Cmadd11.notes)
        assertArrayEquals(arrayOf(Notes.F), Cmadd11.additionalNotes)
    }

    @Test
    fun `should right add 11+ chords`() {
        // todo
//        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G, Notes.F_SHARP), Chord("Cadd11+").notes)
//        assertArrayEquals(arrayOf(Notes.C, Notes.D_SHARP, Notes.G, Notes.F_SHARP), Chord("Cmadd11+").notes)
    }

    @Test
    fun `should right minor add 13- chords`() {
        val Cmadd13minus = Chord("Cmadd13-")
        assertArrayEquals(arrayOf(Notes.C, Notes.D_SHARP, Notes.G), Cmadd13minus.notes)
        assertArrayEquals(arrayOf(Notes.G_SHARP), Cmadd13minus.additionalNotes)
    }

    @Test
    fun `should right suspend chords`() {
        val Csus2 = Chord("Csus2")
        assertArrayEquals(arrayOf(Notes.C, Notes.D, Notes.G), Csus2.notes)
        assertEquals(true, Csus2.isSuspend)
        assertArrayEquals(arrayOf(Notes.C, Notes.F, Notes.G), Chord("Csus4").notes)
        assertArrayEquals(arrayOf(Notes.C, Notes.D, Notes.G), Chord("Cmsus2").notes)
        assertArrayEquals(arrayOf(Notes.C, Notes.F, Notes.G), Chord("Cmsus4").notes)
    }

    @Test
    fun `should right seventh suspend chords`() {
        val C7sus2 = Chord("C7sus2")
        assertArrayEquals(arrayOf(Notes.C, Notes.D, Notes.G), C7sus2.notes)
        assertArrayEquals(arrayOf(Notes.A_SHARP), C7sus2.additionalNotes)
        assertEquals(true, C7sus2.isSuspend)

        val C7sus4 = Chord("C7sus4")
        assertArrayEquals(arrayOf(Notes.C, Notes.F, Notes.G), C7sus4.notes)
        assertArrayEquals(arrayOf(Notes.A_SHARP), C7sus4.additionalNotes)

        val Cm7sus2 = Chord("Cm7sus2")
        assertArrayEquals(arrayOf(Notes.C, Notes.D, Notes.G), Cm7sus2.notes)
        assertArrayEquals(arrayOf(Notes.A_SHARP), Cm7sus2.additionalNotes)

        val Cm7sus4 = Chord("Cm7sus4")
        assertArrayEquals(arrayOf(Notes.C, Notes.F, Notes.G), Cm7sus4.notes)
        assertArrayEquals(arrayOf(Notes.A_SHARP), Cm7sus4.additionalNotes)
    }

    @Test
    fun `should right majadd11+ chord`() {
        // todo
        // assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G, Notes.B, Notes.F_SHARP), Chord("Cmajadd11+").notes)
    }

    @Test
    fun `should right 6add9 chord`() {
        val C6add9 = Chord("C6add9")
        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G), C6add9.notes)
        assertArrayEquals(arrayOf(Notes.A, Notes.D), C6add9.additionalNotes)
    }

    @Test
    fun `should right alternative base chord`() {
        val DslashFsharp = Chord("D/F#")
        assertArrayEquals(arrayOf(Notes.D, Notes.F_SHARP, Notes.A), DslashFsharp.notes)
        assertEquals(Notes.F_SHARP, DslashFsharp.alternativeBaseNote)
    }

    @Test
    fun `should right alternative base C base G chord`() {
        val CslashG = Chord("C/G")
        assertArrayEquals(arrayOf(Notes.C, Notes.E, Notes.G), CslashG.notes)
        assertEquals(Notes.G, CslashG.alternativeBaseNote)
    }
}