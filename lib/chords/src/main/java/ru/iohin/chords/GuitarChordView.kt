package ru.iohin.chords

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Parcel
import android.os.Parcelable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.math.max
import kotlin.math.min

class GuitarChordView : View {

    private var _name: String? = null
    private var _color: Int = Color.BLACK

    private var lineWidth: Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        1f,
        context.resources.displayMetrics
    )

    private var chordScheme: GuitarChord.Scheme? = null

    var name: String?
        get() = _name
        set(value) {
            _name = value
            if (value != null) {
                chordScheme = GuitarChord(value, "EADGBE").getScheme(0)
                requestLayout()
            }
        }

    var color: Int
        get() = _color
        set(value) {
            _color = value
            linePaint.color = value
            dotPaint.color = value
            textPaint.color = value
            invalidate()
        }

    private val minStringIndent = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        8f,
        context.resources.displayMetrics
    )

    private var stringIndent = minStringIndent

    private var minFretIndent = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        16f,
        context.resources.displayMetrics
    )

    private var fretIndent = minFretIndent

    private val minDotRadius = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        3f,
        context.resources.displayMetrics
    )

    private var dotRadius = minDotRadius

    private val linePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = lineWidth
        color = this.color
    }

    private val dotPaint = Paint().apply {
        style = Paint.Style.FILL
        strokeWidth = lineWidth
        color = this.color
    }

    private val minTextSize = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        12f,
        context.resources.displayMetrics
    )

    private val textPaint = TextPaint().apply {
        color = this.color
        textSize = minTextSize
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.GuitarChordView, defStyle, 0
        )

        val name = a.getString(
            R.styleable.GuitarChordView_name
        )
        _name = name
        if (name != null) {
            chordScheme = GuitarChord(name, "EADGBE").getScheme(0)
        }
        _color = a.getColor(
            R.styleable.GuitarChordView_color,
            color
        )
        linePaint.color = color
        dotPaint.color = color
        textPaint.color = color

        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val chordScheme = this.chordScheme
        if (chordScheme != null) {
            val stringsCount = chordScheme.strings.size
            val fretCount = max(chordScheme.maxFret, 3)

            var widthScale = 1f
            var heightScale = 1f

            val measuredWidth = if (widthMode == MeasureSpec.EXACTLY) {
                val calcStringIndent = (width - paddingLeft - paddingRight) / stringsCount
                stringIndent = if (calcStringIndent > minStringIndent) {
                    calcStringIndent.toFloat()
                } else {
                    minStringIndent
                }
                widthScale = stringIndent / minStringIndent
                width
            } else {
                ((stringsCount) * stringIndent).toInt() + paddingLeft + paddingRight
            }

            val measuredHeight = if (heightMode == MeasureSpec.EXACTLY) {
                val calcFretIndent = (height - paddingTop - paddingBottom) / (fretCount + 2)
                fretIndent = if (calcFretIndent > minFretIndent) {
                    calcFretIndent.toFloat()
                } else {
                    minFretIndent
                }
                heightScale = fretIndent / minFretIndent
                height
            } else {
                ((fretCount + 2) * fretIndent).toInt() + paddingTop + paddingBottom
            }

            val scale = min(widthScale, heightScale)

            val dotRadius = minDotRadius * scale
            if (dotRadius > minDotRadius) {
                this.dotRadius = dotRadius
            }

            val textSize = minTextSize * scale
            if (textSize > minTextSize) {
                textPaint.textSize = textSize
            }

            setMeasuredDimension(measuredWidth, measuredHeight)
        } else {
            setMeasuredDimension(width, height)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paddingLeft = paddingLeft + stringIndent / 2
        val paddingTop = paddingTop + fretIndent

        val chordScheme = this.chordScheme ?: return

        chordScheme.mutedStrings.forEach { stringIndex ->
            canvas.drawText(
                "x",
                paddingLeft + (chordScheme.strings.size - stringIndex - 1) * stringIndent,
                paddingTop - fretIndent / 3,
                textPaint
            )
        }

        val maxFret = max(chordScheme.maxFret, 3)

        chordScheme.strings.forEachIndexed { stringIndex, frets ->
            val x = paddingLeft + stringIndex * stringIndent
            canvas.drawLine(
                x,
                paddingTop,
                x,
                paddingTop + maxFret * fretIndent,
                linePaint
            )

            (0 .. maxFret).forEach { fretIndex ->
                val clamped = frets[fretIndex]
                val y = paddingTop + fretIndex * fretIndent
                linePaint.strokeWidth = if (fretIndex == 0) {
                    lineWidth * 2
                } else {
                    lineWidth
                }
                canvas.drawLine(
                    paddingLeft,
                    y,
                    paddingLeft + (chordScheme.strings.size - 1) * stringIndent,
                    y,
                    linePaint
                )
                if (clamped == 1) {
                    if (fretIndex == 0) {
                        dotPaint.style = Paint.Style.STROKE
                    } else {
                        dotPaint.style = Paint.Style.FILL
                    }
                    canvas.drawCircle(
                        paddingLeft + (chordScheme.strings.size - stringIndex - 1) * stringIndent,
                        paddingTop + (fretIndex * fretIndent) - fretIndent / 2,
                        dotRadius,
                        dotPaint
                    )
                }
            }
        }

        canvas.drawText(
            name ?: "",
            (paddingLeft + (chordScheme.strings.size - 1) * stringIndent) / 2 + stringIndent / 2,
            paddingTop + ((maxFret + 1) * fretIndent - fretIndent / 4),
            textPaint
        )
    }

    override fun onSaveInstanceState(): Parcelable {
        return SavedState(super.onSaveInstanceState()).apply {
            chordScheme = this@GuitarChordView.chordScheme
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        chordScheme = state.chordScheme
    }

    class SavedState: BaseSavedState {
        var chordScheme: GuitarChord.Scheme? = null

        constructor(superState: Parcelable?) : super(superState)
        private constructor(source: Parcel?) : super(source) {

        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)

            out.writeParcelable(chordScheme, 0)
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}