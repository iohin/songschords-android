package ru.iohin.chords

import org.junit.Test
import org.junit.Assert.*

class GuitarChordTest {
    @Test
    fun `should right base chords schemes`() {
        val BorCb = """
            ||||||
            ||||||
            |X|||X
            ||||||
            ||XXX|
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            BorCb,
            GuitarChord("Cb", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                |||X|X
                ||||X|
                ||X|||
                |X||||
                ||||||
                ||||||
                ||||||
            """.trimIndent(),
            GuitarChord("C", "EADGBE").getScheme(0).toString()
        )

        val DbOrCsharp = """
            ||||||
            ||||||
            ||||||
            ||||||
            |X|||X
            ||||||
            ||XXX|
        """.trimIndent()

        assertEquals(
            DbOrCsharp,
            GuitarChord("C#", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            DbOrCsharp,
            GuitarChord("Db", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||X|||
                ||||||
                |||X|X
                ||||X|
                ||||||
                ||||||
                ||||||
            """.trimIndent(),
            GuitarChord("D", "EADGBE").getScheme(0).toString()
        )

        val EbOrDsharp = """
            ||||||
            ||X|||
            ||||||
            |||X|X
            ||||X|
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            EbOrDsharp,
            GuitarChord("D#", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            EbOrDsharp,
            GuitarChord("Eb", "EADGBE").getScheme(0).toString()
        )

        val EorFb = """
            X|||XX
            |||X||
            |XX|||
            ||||||
            ||||||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            EorFb,
            GuitarChord("E", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            EorFb,
            GuitarChord("Fb", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||||||
                X|||XX
                |||X||
                |XX|||
                ||||||
                ||||||
                ||||||
            """.trimIndent(),
            GuitarChord("F", "EADGBE").getScheme(0).toString()
        )

        val FsharpOrGb = """
            ||||||
            ||||||
            X|||XX
            |||X||
            |XX|||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            FsharpOrGb,
            GuitarChord("F#", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            FsharpOrGb,
            GuitarChord("Gb", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||XXX|
                ||||||
                |X||||
                X||||X
                ||||||
                ||||||
                ||||||
            """.trimIndent(),
            GuitarChord("G", "EADGBE").getScheme(0).toString()
        )

        val GsharpOrAb = """
            ||||||
            ||||||
            ||||||
            ||||||
            X|||XX
            |||X||
            |XX|||
        """.trimIndent()

        assertEquals(
            GsharpOrAb,
            GuitarChord("G#", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            GsharpOrAb,
            GuitarChord("Ab", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                |X|||X
                ||||||
                ||XXX|
                ||||||
                ||||||
                ||||||
                ||||||
            """.trimIndent(),
            GuitarChord("A", "EADGBE").getScheme(0).toString()
        )

        val AsharpOrBb = """
            ||||||
            |X|||X
            ||||||
            ||XXX|
            ||||||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            AsharpOrBb,
            GuitarChord("A#", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            AsharpOrBb,
            GuitarChord("Bb", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            BorCb,
            GuitarChord("B", "EADGBE").getScheme(0).toString()
        )
    }

    @Test
    fun `should right base minor chords schemes`() {
        val BorCb = """
            ||||||
            ||||||
            |X|||X
            ||||X|
            ||XX||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            BorCb,
            GuitarChord("Cbm", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||||||
                ||||||
                ||||||
                |X|||X
                ||||X|
                ||XX||
                ||||||
            """.trimIndent(),
            GuitarChord("Cm", "EADGBE").getScheme(0).toString()
        )

        val DbOrCsharp = """
            ||||||
            ||||||
            ||||||
            ||||||
            |X|||X
            ||||X|
            ||XX||
        """.trimIndent()

        assertEquals(
            DbOrCsharp,
            GuitarChord("C#m", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            DbOrCsharp,
            GuitarChord("Dbm", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||X|||
                |||||X
                |||X||
                ||||X|
                ||||||
                ||||||
                ||||||
            """.trimIndent(),
            GuitarChord("Dm", "EADGBE").getScheme(0).toString()
        )

        val EbOrDsharp = """
            ||||||
            ||X|||
            |||||X
            |||X||
            ||||X|
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            EbOrDsharp,
            GuitarChord("D#m", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            EbOrDsharp,
            GuitarChord("Ebm", "EADGBE").getScheme(0).toString()
        )

        val EorFb = """
            X||XXX
            ||||||
            |XX|||
            ||||||
            ||||||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            EorFb,
            GuitarChord("Em", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            EorFb,
            GuitarChord("Fbm", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||||||
                X||XXX
                ||||||
                |XX|||
                ||||||
                ||||||
                ||||||
            """.trimIndent(),
            GuitarChord("Fm", "EADGBE").getScheme(0).toString()
        )

        val FsharpOrGb = """
            ||||||
            ||||||
            X||XXX
            ||||||
            |XX|||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            FsharpOrGb,
            GuitarChord("F#m", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            FsharpOrGb,
            GuitarChord("Gbm", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||||||
                ||||||
                ||||||
                X||XXX
                ||||||
                |XX|||
                ||||||
            """.trimIndent(),
            GuitarChord("Gm", "EADGBE").getScheme(0).toString()
        )

        val GsharpOrAb = """
            ||||||
            ||||||
            ||||||
            ||||||
            X||XXX
            ||||||
            |XX|||
        """.trimIndent()

        assertEquals(
            GsharpOrAb,
            GuitarChord("G#m", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            GsharpOrAb,
            GuitarChord("Abm", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                |X|||X
                ||||X|
                ||XX||
                ||||||
                ||||||
                ||||||
                ||||||
            """.trimIndent(),
            GuitarChord("Am", "EADGBE").getScheme(0).toString()
        )

        val AsharpOrBb = """
            ||||||
            |X|||X
            ||||X|
            ||XX||
            ||||||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            AsharpOrBb,
            GuitarChord("A#m", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            AsharpOrBb,
            GuitarChord("Bbm", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            BorCb,
            GuitarChord("Bm", "EADGBE").getScheme(0).toString()
        )
    }

    @Test
    fun `should right seventh chords schemes`() {
        val BorCb = """
            ||||||
            ||||||
            |X|X|X
            ||||||
            ||X|X|
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            BorCb,
            GuitarChord("Cb7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||||||
                ||||||
                ||||||
                |X|X|X
                ||||||
                ||X|X|
                ||||||
            """.trimIndent(),
            GuitarChord("C7", "EADGBE").getScheme(0).toString()
        )

        val CsharpOrDb = """
            ||||||
            ||||||
            ||||||
            ||||||
            |X|X|X
            ||||||
            ||X|X|
        """.trimIndent()

        assertEquals(
            CsharpOrDb,
            GuitarChord("C#7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            CsharpOrDb,
            GuitarChord("Db7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||X|||
                ||||X|
                |||X|X
                ||||||
                ||||||
                ||||||
                ||||||
            """.trimIndent(),
            GuitarChord("D7", "EADGBE").getScheme(0).toString()
        )

        val DsharpOrEb = """
            ||||||
            ||X|||
            ||||X|
            |||X|X
            ||||||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            DsharpOrEb,
            GuitarChord("D#7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            DsharpOrEb,
            GuitarChord("Eb7", "EADGBE").getScheme(0).toString()
        )

        val EorFb = """
            X|X|XX
            |||X||
            |X||||
            ||||||
            ||||||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            EorFb,
            GuitarChord("E7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            EorFb,
            GuitarChord("Fb7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||||||
                X|X|XX
                |||X||
                |X||||
                ||||||
                ||||||
                ||||||
            """.trimIndent(),
            GuitarChord("F7", "EADGBE").getScheme(0).toString()
        )

        val FsharpOrGb = """
            ||||||
            ||||||
            X|X|XX
            |||X||
            |X||||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            FsharpOrGb,
            GuitarChord("F#7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            FsharpOrGb,
            GuitarChord("Gb7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||||||
                ||||||
                ||||||
                X|X|XX
                |||X||
                |X||||
                ||||||
            """.trimIndent(),
            GuitarChord("G7", "EADGBE").getScheme(0).toString()
        )

        val GsharpOrAb = """
            ||||||
            ||||||
            ||||||
            ||||||
            X|X|XX
            |||X||
            |X||||
        """.trimIndent()

        assertEquals(
            GsharpOrAb,
            GuitarChord("G#7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            GsharpOrAb,
            GuitarChord("Ab7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                |X|X|X
                ||||||
                ||X|X|
                ||||||
                ||||||
                ||||||
                ||||||
            """.trimIndent(),
            GuitarChord("A7", "EADGBE").getScheme(0).toString()
        )

        val AsharpOrBb = """
            ||||||
            |X|X|X
            ||||||
            ||X|X|
            ||||||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            AsharpOrBb,
            GuitarChord("A#7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            AsharpOrBb,
            GuitarChord("Bb7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            BorCb,
            GuitarChord("B7", "EADGBE").getScheme(0).toString()
        )
    }

    @Test
    fun `should right seventh minor chords schemes`() {
        val BorCb = """
            ||||||
            ||||||
            |X|X|X
            ||||X|
            ||X|||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            BorCb,
            GuitarChord("Cbm7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||||||
                ||||||
                ||||||
                |X|X|X
                ||||X|
                ||X|||
                ||||||
            """.trimIndent(),
            GuitarChord("Cm7", "EADGBE").getScheme(0).toString()
        )

        val CsharpOrDb = """
            ||||||
            ||||||
            ||||||
            ||||||
            |X|X|X
            ||||X|
            ||X|||
        """.trimIndent()

        assertEquals(
            CsharpOrDb,
            GuitarChord("C#m7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            CsharpOrDb,
            GuitarChord("Dbm7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||X|||
                ||||XX
                |||X||
                ||||||
                ||||||
                ||||||
                ||||||
            """.trimIndent(),
            GuitarChord("Dm7", "EADGBE").getScheme(0).toString()
        )

        val DsharpOrEb = """
            ||||||
            ||X|||
            ||||XX
            |||X||
            ||||||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            DsharpOrEb,
            GuitarChord("D#m7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            DsharpOrEb,
            GuitarChord("Ebm7", "EADGBE").getScheme(0).toString()
        )

        val EorFb = """
            X|XXXX
            ||||||
            |X||||
            ||||||
            ||||||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            EorFb,
            GuitarChord("Em7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            EorFb,
            GuitarChord("Fbm7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||||||
                X|XXXX
                ||||||
                |X||||
                ||||||
                ||||||
                ||||||
            """.trimIndent(),
            GuitarChord("Fm7", "EADGBE").getScheme(0).toString()
        )

        val FsharpOrGb = """
            ||||||
            ||||||
            X|XXXX
            ||||||
            |X||||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            FsharpOrGb,
            GuitarChord("F#m7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            FsharpOrGb,
            GuitarChord("Gbm7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                ||||||
                ||||||
                ||||||
                X|XXXX
                ||||||
                |X||||
                ||||||
            """.trimIndent(),
            GuitarChord("Gm7", "EADGBE").getScheme(0).toString()
        )

        val GsharpOrAb = """
            ||||||
            ||||||
            ||||||
            ||||||
            X|XXXX
            ||||||
            |X||||
        """.trimIndent()

        assertEquals(
            GsharpOrAb,
            GuitarChord("G#m7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            GsharpOrAb,
            GuitarChord("Abm7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            """
                |X|X|X
                ||||X|
                ||X|||
                ||||||
                ||||||
                ||||||
                ||||||
            """.trimIndent(),
            GuitarChord("Am7", "EADGBE").getScheme(0).toString()
        )

        val AsharpOrBb = """
            ||||||
            |X|X|X
            ||||X|
            ||X|||
            ||||||
            ||||||
            ||||||
        """.trimIndent()

        assertEquals(
            AsharpOrBb,
            GuitarChord("A#m7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            AsharpOrBb,
            GuitarChord("Bbm7", "EADGBE").getScheme(0).toString()
        )

        assertEquals(
            BorCb,
            GuitarChord("Bm7", "EADGBE").getScheme(0).toString()
        )
    }
}