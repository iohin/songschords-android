package ru.iohin.chords

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.lang.Integer.min

class GuitarChord(chordName: String, val tuning: String): Chord(chordName) {
    private fun isSimpleChord() = arrayOf("C", "G").contains(chordName)

    private fun haveAddNotes() = additionalNotes.isNotEmpty()

    fun getScheme(position: Int): Scheme {
        val mostMaxFret = 6
        val strings = List(tuning.length) { MutableList(mostMaxFret + 1) { 0 } }
        val soundedStrings = MutableList(tuning.length) { 0 }
        val fretRange = 4
        var minFret = 0
        var maxFret = fretRange
        var maxUsedFret = 0
        var maxStringIndex = if (
            isSuspend && notes.size >= 3 && Notes.chordStep(
                baseNote,
                Mode.MINOR,
                2
            ) == notes[1]
        ) {
            strings.size - 2
        } else {
            strings.size - 1
        }
        var baseWasSet = false
        var nextNoteIndex = 0
        var nextAddNoteIndex = 0
        var note = notes.getOrNull(0) ?: ""
        val attemptCount = notes.size + additionalNotes.size
        var attempt = 1

        val nextNote = {
            if (isSuspend && nextNoteIndex == 1 && maxStringIndex > 2) {
                nextNoteIndex++
            }
            if (nextNoteIndex <= notes.size - 1) {
                note = notes[nextNoteIndex]
                nextNoteIndex++
            } else if (nextAddNoteIndex <= additionalNotes.size - 1) {
                note = additionalNotes[nextAddNoteIndex]
                nextAddNoteIndex++
            } else {
                note = notes[0]
                nextAddNoteIndex = 0
                nextNoteIndex = 1
            }
        }

        nextNote()

        while (notes.isNotEmpty() && maxStringIndex >= 0) {
            run notes@{
                val searchNote = if (baseWasSet) {
                    note
                } else {
                    alternativeBaseNote ?: note
                }

                (maxStringIndex downTo 0).forEach { stringIndex ->
                    maxStringIndex = stringIndex

                    var checkNote = tuning[tuning.length - 1 - stringIndex].toString()
                    checkNote = Notes.after(checkNote, minFret)

                    (minFret..min(maxFret, mostMaxFret)).forEach { fretIndex ->
                        if (searchNote == checkNote && fretIndex in (minFret..maxFret)) {
                            strings[stringIndex][fretIndex] = 1
                            soundedStrings[stringIndex] = 1

                            if (fretIndex > maxUsedFret) {
                                maxUsedFret = fretIndex
                            }

                            if (!baseWasSet) {
                                baseWasSet = true
                                if (!isSimpleChord() || haveAddNotes() || isSuspend) {
                                    minFret = fretIndex
                                    maxFret = minFret + if (minFret > 0) {
                                        fretRange - 1
                                    } else {
                                        fretRange
                                    }
                                }
                            }

                            maxStringIndex = stringIndex - 1

                            nextNote()

                            attempt = 1

                            return@notes
                        }
                        checkNote = Notes.after(checkNote)
                    }
                    if (!baseWasSet) {
                        maxStringIndex--
                    } else if (soundedStrings[stringIndex] == 0) {
                        nextNote()
                        if (attempt >= attemptCount) {
                            attempt = 1
                            maxStringIndex--
                        }
                        attempt++
                        return@notes
                    }
                }
            }
        }

        return Scheme(
            position,
            strings.map { it.toIntArray() }.toTypedArray(),
            soundedStrings.toIntArray(),
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
        val soundedStrings: IntArray,
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