package ru.iohin.chords

object Notes {
    const val C = "C"
    const val C_SHARP = "C#"
    const val D = "D"
    const val D_SHARP = "D#"
    const val E = "E"
    const val F = "F"
    const val F_SHARP = "F#"
    const val G = "G"
    const val G_SHARP = "G#"
    const val A = "A"
    const val A_SHARP = "A#"
    const val B = "B"

    val notes = arrayOf(C, C_SHARP, D, D_SHARP, E, F, F_SHARP, G, G_SHARP, A, A_SHARP, B)

    fun flatToSharp(note: String): String {
        if (note.length < 2) {
            return note
        }
        val naturalNote = note[0].uppercase()
        if (note[1].lowercase() != "b") {
            return note
        }
        return before(naturalNote)
    }

    fun before(note: String): String {
        if (note.length > 2) {
            return note
        }
        var index = notes.indexOf(note)
        if (index < 0) {
            return note
        }
        if (index > 0) {
            index--
        } else {
            index = notes.size - 1
        }
        return notes[index]
    }

    fun after(note: String): String {
        if (note.length > 2) {
            return note
        }
        var index = notes.indexOf(note)
        if (index < 0) {
            return note
        }
        if (index == notes.size - 1) {
            index = 0
        } else {
            index++
        }
        return notes[index]
    }

    private fun getNoteIndexForStep(step: Int, mode: Mode): Int {
        val indexes = if (mode == Mode.MAJOR) {
            intArrayOf(0, 2, 4, 5, 7, 9, 11)
        } else {
            intArrayOf(0, 2, 3, 5, 7, 8, 10)
        }
        return if (step >= indexes.size) {
            indexes[step % indexes.size]
        } else {
            indexes[step]
        }
    }

    fun chordStep(chord: String, mode: Mode, step: Int): String {
        if (step == 0) {
            return chord
        }
        val baseNote = if (chord.length > 1) {
            chord.substring(0, 2)
        } else {
            chord
        }
        val baseIndex = notes.indexOf(baseNote)
        if (baseIndex < 0) {
            return chord
        }
        var stepIndex = baseIndex + getNoteIndexForStep(step - 1, mode)
        if (stepIndex >= notes.size) {
            stepIndex -= notes.size
        }
        return notes[stepIndex]
    }
}