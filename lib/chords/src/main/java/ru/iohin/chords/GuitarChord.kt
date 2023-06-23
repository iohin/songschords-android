package ru.iohin.chords

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.lang.Integer.min

class GuitarChord(chordName: String, val tuning: String): Chord(chordName) {
    private fun isSimpleChord(): Boolean {
        // simple chords in zero position
        val simpleChords = arrayOf("C", "D", "E", "G", "A", "Dm", "Em", "Am")
        return if (alternativeBaseNote != null) {
            // if chord has alternative base note check base chord name without alternative base
            val baseChord = chordName
                .replace(alternativeBaseNote, "")
                .replace("/", "")
            simpleChords.contains(baseChord)
        } else {
            simpleChords.contains(chordName)
        }
    }

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
            // sus2 chords cannot build with base on 6 string, therefore start from 5 string
            strings.size - 2
        } else {
            strings.size - 1
        }
        var baseWasSet = false
        var nextNoteIndex = 0
        var nextAddNoteIndex = 0
        var note = notes.getOrNull(0) ?: ""
        // attempt count to search note on one string
        val attemptCount = notes.size + additionalNotes.size
        var attempt = 1

        val nextNote = {
            // suspend note should be set once in higher position
            if (isSuspend && nextNoteIndex == 1 && maxStringIndex > 2) {
                nextNoteIndex++
            }
            // in maj chord with base on 6 string skip 3th step in lower position
            if (isMaj && nextNoteIndex == 2 && maxStringIndex > 3) {
                nextNoteIndex++
            }
            if (nextNoteIndex <= notes.size - 1) {
                note = notes[nextNoteIndex]
                nextNoteIndex++
            } else if (nextAddNoteIndex <= additionalNotes.size - 1) {
                note = additionalNotes[nextAddNoteIndex]
                nextAddNoteIndex++
            } else {
                // in maj chord higher note should not be base note
                note = if (isMaj && notes.size > 1) {
                    nextNoteIndex = 2
                    notes[1]
                } else {
                    nextNoteIndex = 1
                    notes[0]
                }
                nextAddNoteIndex = 0
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
                                // for non simple chords shift frets range from base note fret
                                if (!isSimpleChord() || haveAddNotes() || isSuspend) {
                                    minFret = fretIndex
                                    maxFret = minFret + if (minFret > 0) {
                                        // if min fret higher 0 then reduce range for simplify
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
                        if (attempt >= attemptCount) {
                            attempt = 1
                            maxStringIndex--
                        }
                        attempt++
                        nextNote()
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