package ru.iohin.chords

/**
chord types:

5
5-
6
7
maj
9
9-
add9
dim7
maj6
maj9
add11+
majadd11+
+
add11
75+
sus4
sus2
7sus4
6add9

m
m4
m5-
m6
m7
m7+
m9
madd9
madd11
madd11+
msus4
m+
msus2
madd13-
m75-
m7sus4
m6add9
m7sus2
m7add11
m75+
 */
open class Chord(val chordName: String) {
    val baseNote: String
        get() = notes.getOrNull(0) ?: ""
    val notes: Array<String>
    val mode: Mode

    init {
        var baseNote = chordName[0].uppercase()
        if (baseNote.uppercase() == "H") {
            baseNote = "B"
        }
        var chordParts = chordName.substring(1)
        if (chordParts.isNotEmpty() && chordParts[0] == '#') {
            baseNote += chordParts[0]
            chordParts = chordParts.substring(1)
        } else if (chordParts.isNotEmpty() && chordParts[0].lowercase() == "b") {
            baseNote = Notes.before(baseNote)
            chordParts = chordParts.substring(1)
        }
        val notes = mutableListOf<String>()
        notes.add(baseNote)
        mode = if (
            chordParts.isNotEmpty()
            && chordParts[0].lowercase() == "m"
            && chordParts.indexOf("maj") < 0
        ) {
            chordParts = chordParts.substring(1)
            Mode.MINOR
        } else {
            Mode.MAJOR
        }
        notes.add(Notes.chordStep(baseNote, mode, 3))
        notes.add(Notes.chordStep(baseNote, mode, 5))
        if (chordParts.isNotEmpty() && chordParts[0] == '7') {
            notes.add(Notes.chordStep(baseNote, Mode.MINOR, 7))
        }

        this.notes = notes.toTypedArray()
    }
}