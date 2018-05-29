package skymusic.com.vn.karaoke.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import skymusic.com.vn.karaoke.R
import java.util.*

class HorVoiceView : View {

    private var paint: Paint? = null
    private var color: Int? = 0
    private var lineHeight = 8f
    private var maxLineheight: Float? = 0f
    private var lineWidth: Float? = 0f
    private var textSize: Float? = 0f
    private var text = " 15 "
    private var textColor: Int? = 0
    private var milliSeconds: Int = 0
    private var isStart = false
    private var mRunable: Runnable? = null

    internal var list = LinkedList<Int>()

    constructor(context: Context) : super(context)

    @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        for (i in 0..9) {
            list.add(1)
        }
        paint = Paint()
        mRunable = Runnable {
            while (isStart) {
                milliSeconds += 200
                text = if (milliSeconds / 1000 < 10) "0" + milliSeconds / 1000 else (milliSeconds / 1000).toString() + ""
                Log.e("horvoiceview", "text $text")
                setVoice(1)
                try {
                    Thread.sleep(200)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.HorVoiceView)
        color = mTypedArray.getColor(R.styleable.HorVoiceView_voiceLineColor, Color.BLACK)
        lineWidth = mTypedArray.getDimension(R.styleable.HorVoiceView_voiceLineWidth, 35f)
        lineHeight = mTypedArray.getDimension(R.styleable.HorVoiceView_voiceLineHeight, 8f)
        maxLineheight = mTypedArray.getDimension(R.styleable.HorVoiceView_voiceLineHeight, 32f)
        textSize = mTypedArray.getDimension(R.styleable.HorVoiceView_voiceTextSize, 45f)
        textColor = mTypedArray.getColor(R.styleable.HorVoiceView_voiceTextColor, Color.BLACK)
        mTypedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val widthcentre = width / 2
        val heightcentre = height / 2
        paint?.apply {
            strokeWidth = 0f
            color = textColor?:0
            textSize = textSize
            typeface = Typeface.DEFAULT_BOLD
            val textWidth = measureText(text)
            canvas.drawText(text, widthcentre - textWidth / 2, heightcentre - (ascent() + descent()) / 2, paint)
            color = color
            style = Paint.Style.FILL
            strokeWidth = lineWidth?:0f
            isAntiAlias = true
            for (i in 0..9) {
                val rect = RectF(widthcentre.toFloat() + 2f * i.toFloat() * lineHeight + textWidth / 2 + lineHeight, heightcentre - list[i] * lineHeight / 2, widthcentre.toFloat() + 2f * i.toFloat() * lineHeight + 2 * lineHeight + textWidth / 2, heightcentre + list[i] * lineHeight / 2)
                val rect2 = RectF(widthcentre - (2f * i.toFloat() * lineHeight + 2 * lineHeight + textWidth / 2), heightcentre - list[i] * lineHeight / 2, widthcentre - (2f * i.toFloat() * lineHeight + textWidth / 2 + lineHeight), heightcentre + list[i] * lineHeight / 2)
                canvas.drawRect(rect, paint)
                canvas.drawRect(rect2, paint)
            }
        }
    }

    @Synchronized
    fun setVoice(height: Int?) {
        for (i in 0..height!! / 30) {
            list.removeAt(9 - i)
            list.add(i, if (height / 20 - i < 1) 1 else height / 20 - i)
        }
        postInvalidate()
    }

    @Synchronized
    fun setText(text: String) {
        this.text = text
        postInvalidate()
    }

    @Synchronized
    fun startRecording() {
        milliSeconds = 0
        isStart = true
        Thread(mRunable).start()
    }

    @Synchronized
    fun stopRecord() {
        isStart = false
        list.clear()
        for (i in 0..9) {
            list.add(1)
        }
        text = "00"
        postInvalidate()
    }
}