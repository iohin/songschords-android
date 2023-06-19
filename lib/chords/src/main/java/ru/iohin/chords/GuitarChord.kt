package ru.iohin.chords

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class GuitarChord(chordName: String, val tuning: String): Chord(chordName) {
    fun getScheme(position: Int): Scheme {
        val strings = List(tuning.length) { MutableList(7) { 0 } }
        val mutedStrings = mutableListOf<Int>()
        var baseWasSet = false
        val fretRange = 4
        var minFret = 0
        var maxFret = fretRange
        val maxFretBase = fretRange
        var maxUsedFret = 0
        var addNoteWasSet = false
        (strings.size - 1 downTo 0).forEach { stringIndex ->
            val frets = strings[stringIndex]
            var note = tuning[tuning.length - 1 - stringIndex].toString()
            var noteIsSet = false
            var fretIndexWasSet = -1
            frets.forEachIndexed { fretIndex, _ ->
                if (
                    (!noteIsSet || notes.size > 3 && notes[3] == note && !addNoteWasSet && stringIndex < 3)
                    && fretIndex >= minFret
                    && fretIndex <= maxFret
                    && notes.contains(note)
                    && (baseWasSet || notes[0] == note && fretIndex <= maxFretBase)
                ) {
                    if (notes.size > 3 && notes[3] == note) {
                        addNoteWasSet = true
                    }
                    strings[stringIndex][fretIndex] = 1
                    if (!baseWasSet && (fretIndex < 3 || mode == Mode.MINOR || notes.size > 3 && fretIndex >= maxFret)) {
                        minFret = fretIndex
                        maxFret = minFret + fretRange
                    }
                    baseWasSet = true
                    noteIsSet = true
                    if (fretIndexWasSet > -1) {
                        strings[stringIndex][fretIndexWasSet] = 0
                    }
                    if (fretIndex > maxUsedFret) {
                        maxUsedFret = fretIndex
                    }
                    fretIndexWasSet = fretIndex
                }
                note = Notes.after(note)
            }
            if (!noteIsSet) {
                mutedStrings.add(stringIndex)
            }
        }
        return Scheme(
            position,
            strings.map { it.toIntArray() }.toTypedArray(),
            mutedStrings.toIntArray(),
            minFret,
            maxUsedFret,
            null
        )
    }

    @Parcelize
    class Scheme(
        val position: Int,
        /**
         * array of strings of arrays of frets
         */
        val strings: Array<IntArray>,
        val mutedStrings: IntArray,
        val minFret: Int,
        val maxFret: Int,
        val barre: Barre?
    ): Parcelable {
        override fun toString(): String {
            val string = StringBuilder()
            strings[0].forEachIndexed { fretIndex, i ->
                (strings.size - 1 downTo 0).forEach {
                    string.append(if (strings[it][fretIndex] == 0) "|" else "X")
                }
                string.append("\n")
            }
            return string.toString().trim()
        }
    }

    @Parcelize
    data class Barre(
        val fret: Int,
        val startString: Int,
        val endString: Int
    ): Parcelable
}