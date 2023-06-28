package ru.iohin.chords

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout

class ChordsListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    private var _color: Int = Color.BLACK
    var color: Int
        get() = _color
        set(value) {
            _color = value
            updateColors()
        }

    private val flow = Flow(context)

    private var _chords = emptyArray<String>()
    var chords: Array<String>
        get() = _chords
        set(value) {
            _chords = value
            populateChords()
        }

    init {
        try {
            val a = context.obtainStyledAttributes(
                attrs, R.styleable.ChordsListView, defStyleAttr, 0
            )
            _color = a.getColor(
                R.styleable.ChordsListView_cordColor,
                color
            )

            a.recycle()
        } catch (ignored: Throwable) {}

        flow.setWrapMode(Flow.WRAP_CHAIN)
        flow.setHorizontalStyle(Flow.CHAIN_PACKED)
        flow.setVerticalStyle(Flow.CHAIN_PACKED)
        flow.setHorizontalBias(0f)
        flow.setVerticalBias(0f)
        val gap = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            10f,
            context.resources.displayMetrics
        ).toInt()
        flow.setHorizontalGap(gap)
        flow.setVerticalGap(gap)
        addView(flow)
    }

    fun setChordsAnimated(chords: Array<String>) {
        _chords = chords
        populateChords(true)
    }

    private fun clearChords() {
        (childCount - 1 downTo 0).forEach { childIndex ->
            if (getChildAt(childIndex) is GuitarChordView) {
                removeViewAt(childIndex)
            }
        }
    }

    private fun populateChords(animated: Boolean = false) {
        clearChords()
        val ids = mutableListOf<Int>()
        chords.forEachIndexed { index, chord ->
            val chordView = GuitarChordView(context).apply {
                id = index + 1
                if (animated) {
                    setNameAnimated(chord)
                } else {
                    name = chord
                }
                color = this@ChordsListView.color
            }
            addView(chordView)
            ids.add(chordView.id)
        }
        flow.referencedIds = ids.toIntArray()
    }

    private fun updateColors() {
        (0 until childCount).forEach {
            val chordView = getChildAt(it)
            if (chordView is GuitarChordView) {
                chordView.color = color
            }
        }
    }
}