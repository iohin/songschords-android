package ru.iohin.chords

/**
chord types:

+
5
5-
6
7
9
9-
75+
maj
maj6
maj9
majadd11+
add9
add11
add11+
6add9
dim7
sus4
sus2
7sus4

m
m+
m4
m5-
m6
m7
m7+
m9
m75-
m75+
madd9
madd11
madd11+
madd13-
m6add9
m7add11
msus4
msus2
m7sus4
m7sus2
 */
open class Chord(val chordName: String) {
    val baseNote: String
        get() = notes.getOrNull(0) ?: ""
    val notes: Array<String>
    val mode: Mode

    val alternativeBaseNote: String?
    val additionalNotes: Array<String>
    val isSuspend: Boolean
    val isMaj: Boolean

    init {
        // detect base note
        var baseNote = chordName[0].uppercase()
        if (baseNote.uppercase() == "H") {
            baseNote = "B"
        }

        // detect alteration
        var chordParts = chordName.substring(1)
        if (chordParts.isNotEmpty() && chordParts[0] == '#') {
            baseNote += chordParts[0]
            chordParts = chordParts.substring(1)
        } else if (chordParts.isNotEmpty() && chordParts[0].lowercase() == "b") {
            baseNote = Notes.before(baseNote)
            chordParts = chordParts.substring(1)
        }

        // add base note to set
        val notes = mutableListOf<String>()
        notes.add(baseNote)

        // detect mode minor or major
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

        val calcMode = { fromIndex: Int ->
            var alter = false
            val mode = if (chordParts.indexOf("+") == fromIndex + 1) {
                alter = true
                Mode.MAJOR
            } else if (chordParts.indexOf("-") == fromIndex + 1) {
                alter = true
                Mode.MINOR
            } else {
                mode
            }
            Pair(mode, alter)
        }

        // if fifth cord 3 step not added
        if (mode == Mode.MINOR || chordParts.isEmpty() || chordParts[0] != '5' && chordParts[0] != '4') {
            notes.add(Notes.chordStep(baseNote, mode, 3))
        }

        // if fourth cord then add 4 step instead of 5
        if (chordParts.isNotEmpty() && chordParts[0] == '4') {
            notes.add(Notes.chordStep(baseNote, calcMode(0).first, 4))
        } else if (chordParts.isNotEmpty() && chordParts[0] == '+') {
            // detect aug
            notes.add(Notes.chordStep(baseNote, Mode.MINOR, 6))
            chordParts = chordParts.substring(1)
        } else {
            notes.add(Notes.chordStep(baseNote, calcMode(0).first, 5))
        }

        val additionalNotes = mutableListOf<String>()

        // detect extended steps
        while (chordParts.isNotEmpty() && """\d+""".toRegex().containsMatchIn(chordParts[0].toString())) {
            val extendStep = chordParts[0].toString().toInt()
            val (modeToApply, alter) = calcMode(0)
            if (extendStep > 7) {
                additionalNotes.add(Notes.chordStep(baseNote, Mode.MINOR, 7))
            }
            if (extendStep == 7 && !alter) {
                additionalNotes.add(Notes.chordStep(baseNote, Mode.MINOR, extendStep))
            } else if (extendStep != 4 && extendStep != 5) {
                additionalNotes.add(Notes.chordStep(baseNote, modeToApply, extendStep))
            }
            chordParts = chordParts.substring(if (alter) 2 else 1)
        }

        // detect maj
        if (chordParts.isNotEmpty() && chordParts.indexOf("maj") == 0) {
            var offset = 3
            val majStep = if (chordParts.length > 3 && """\d+""".toRegex().containsMatchIn(chordParts[3].toString())) {
                offset = 4
                chordParts[3].toString().toInt()
            } else {
                7
            }
            if (majStep > 7) {
                additionalNotes.add(Notes.chordStep(baseNote, Mode.MAJOR, 7))
                additionalNotes.add(Notes.chordStep(baseNote, mode, majStep))
            } else {
                additionalNotes.add(Notes.chordStep(baseNote, Mode.MAJOR, majStep))
            }
            chordParts = chordParts.substring(offset)
            isMaj = true
        } else {
            isMaj = false
        }

        // detect additional step
        if (chordParts.isNotEmpty() && chordParts.indexOf("add") == 0 && chordParts.length > 3) {
            chordParts = chordParts.substring(3)
            var searchIndex = 0
            val stepBuilder = StringBuilder()
            while (
                searchIndex < chordParts.length
                && """\d+""".toRegex().containsMatchIn(chordParts[searchIndex].toString())
            ) {
                stepBuilder.append(chordParts[searchIndex])
                searchIndex++
            }
            val addStepString = stepBuilder.toString()
            val addStep = addStepString.toInt()
            val (modeToApply, alter) = calcMode(addStepString.length - 1)

            additionalNotes.add(Notes.chordStep(baseNote, modeToApply, addStep))

            val offset = addStepString.length + if (alter) 1 else 0
            chordParts = chordParts.substring(offset)
        }

        // detect suspend step
        if (
            chordParts.isNotEmpty()
            && chordParts.indexOf("sus") == 0
            && chordParts.length > 3
            && """\d+""".toRegex().containsMatchIn(chordParts[3].toString())) {
            val susStep = chordParts[3].toString().toInt()
            notes[1] = Notes.chordStep(baseNote, mode, susStep)
            isSuspend = true
        } else {
            isSuspend = false
        }

        // detect alternative base step
        val slashBaseIndex = chordParts.indexOf("/")
        alternativeBaseNote = if (slashBaseIndex > -1) {
            chordParts.substring(slashBaseIndex + 1, chordParts.length)
        } else {
            null
        }

        this.notes = notes.toTypedArray()
        this.additionalNotes = additionalNotes.toTypedArray()
    }
}