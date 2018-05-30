package skymusic.com.vn.karaoke.widget

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import skymusic.com.vn.karaoke.R
import skymusic.com.vn.karaoke.utils.AudioRecordManager
import skymusic.com.vn.karaoke.utils.VibratorUtils
import java.io.File

class RecorderButton @JvmOverloads constructor(private val mCtx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatButton(mCtx, attrs, defStyleAttr), AudioRecordManager.AudioStateListener {

    private var mCurState = STATE_NORMAL
    private var isRecording = false // 已经开始录音

    private var mAudioRecordManager: AudioRecordManager? = null

    private var mTime: Float = 0.toFloat()
    // 是否触发longclick
    private var mReady: Boolean = false
    private val str_recorder_normal: String?
    private val str_recorder_recording: String?
    private val str_recorder_want_cancel: String?
    private val bg_recorder_normal: Int
    private val bg_recorder_recording: Int
    private val bg_recorder_cancel: Int
    private var max_record_time: Float = 0f
    private var min_record_time: Float = 0f
    private var max_voice_level: Int = 0
    private var mListener: AudioStateRecorderListener? = null

    /**
     * 获取音量大小的Runnable
     */
    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            mListener?.apply {
                when (msg.what) {
                    MSG_AUDIO_PREPARED -> {

                        // audio end prepared以后开始录音
                        isRecording = true
                        onStart(mTime)
                        Thread({
                            while (isRecording) {
                                try {
                                    Thread.sleep(100)
                                    mTime += 0.1f
                                    sendEmptyMessage(MSG_UPDATE_TIME)
                                    sendEmptyMessage(MSG_VOICE_CHANGE)
                                    if (mTime >= max_record_time) {
                                        mAudioRecordManager?.release()
                                        sendEmptyMessage(MSG_TIME_LIMIT)
                                    }
                                } catch (e: InterruptedException) {
                                    e.printStackTrace()
                                }
                            }
                        }).start()
                    }
                    MSG_VOICE_CHANGE -> if (mListener != null) {
                        onVoiceChange(mAudioRecordManager?.getVoiceLevel(max_voice_level)!!)
                    }
                    MSG_UPDATE_TIME -> if (mListener != null) {
                        onUpdateTime(mTime, min_record_time, max_record_time)
                    }
                    MSG_TIME_LIMIT -> {
                        if (mListener != null) {
                            MediaPlayer.create(mCtx, R.raw.gj).start()
                            onFinish(mTime, mAudioRecordManager?.currentFilePath!!)
                        }
                        changeState(STATE_NORMAL)
                        reset()
                    }
                }
            }

            super.handleMessage(msg)
        }
    }

    init {

        val a = mCtx.obtainStyledAttributes(attrs,
                R.styleable.RecorderButton)

        str_recorder_normal = a.getString(R.styleable.RecorderButton_txt_normal)
        str_recorder_recording = a.getString(R.styleable.RecorderButton_txt_recording)
        str_recorder_want_cancel = a.getString(R.styleable.RecorderButton_txt_want_cancel)

        bg_recorder_normal = a.getResourceId(R.styleable.RecorderButton_bg_normal, 0)
        bg_recorder_recording = a.getResourceId(R.styleable.RecorderButton_bg_recording, 0)
        bg_recorder_cancel = a.getResourceId(R.styleable.RecorderButton_bg_want_cancel, 0)

        //最大录音时间，默认为15秒
        //最小录音时间，默认为10秒
        max_record_time = a.getFloat(R.styleable.RecorderButton_max_record_time, 15f)
        min_record_time = a.getFloat(R.styleable.RecorderButton_min_record_time, 10f)

        max_voice_level = a.getInt(R.styleable.RecorderButton_max_voice_level, 5)

        a.recycle()

        val dir = Environment.getExternalStorageDirectory().toString() + File.separator + "sxbb" + File.separator + "record"
        mAudioRecordManager = AudioRecordManager.getInstance(dir)
        mAudioRecordManager?.setOnAudioStateListner(this)

        text = str_recorder_normal
        setBackgroundResource(bg_recorder_normal)

        //        setOnClickListener(new OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                mHandler.postDelayed(new Runnable() {
        //                    @Override
        //                    public void run() {
        //                        mReady = true;
        //                        Log.e(TAG, "OnLongClick");
        //
        //                        //播放提示音
        //                        MediaPlayer.create(mCtx, R.raw.fx).start();
        //                        mAudioRecordManager.prepareAudio();
        //                    }
        //                }, 1000);
        //            }
        //        });

        setOnLongClickListener {
            mReady = true
            Log.e(TAG, "OnLongClick")

            VibratorUtils.vibrate(mCtx, 60)

            //播放提示音
            MediaPlayer.create(mCtx, R.raw.fx).start()
            mAudioRecordManager?.prepareAudio()

            false
        }
    }

    /**
     * 录音完成后的回调
     */
    interface AudioStateRecorderListener {
        fun onFinish(seconds: Float, filePath: String)

        fun onCancel(isTooShort: Boolean)

        fun onVoiceChange(voiceLevel: Int)

        fun onStart(time: Float)

        fun onUpdateTime(currentTime: Float, minTime: Float, maxTime: Float)

        fun onReturnToRecord()

        fun onWantToCancel()
    }

    fun setAudioStateRecorderListener(listener: AudioStateRecorderListener) {
        mListener = listener
    }

    override fun wellPrepared() {
        Log.d("LONG", "wellPrepared")
        mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        val x = event.x.toInt()
        val y = event.y.toInt()

        when (action) {
            MotionEvent.ACTION_DOWN -> if (mReady) {
                changeState(STATE_RECORDING)
            }
            MotionEvent.ACTION_MOVE ->
                // 根据x, y的坐标，判断是否想要取消
                if (mReady) {
                    if (wantToCancel(x, y)) {
                        changeState(STATE_WANT_TO_CANCEL)
                        if (mListener != null) {
                            mListener!!.onWantToCancel()
                        }
                    } else {
                        changeState(STATE_RECORDING)
                        if (mListener != null) {
                            mListener!!.onReturnToRecord()
                        }
                    }
                }
            MotionEvent.ACTION_UP -> {
                /**
                 * 1. 未触发onLongClick
                 * 2. prepared没有完毕已经up
                 * 3. 录音时间小于预定的值，这个值我们设置为在onLongClick之前
                 */
                if (!mReady) { // 未触发onLongClick
                    changeState(STATE_NORMAL)
                    reset()
                    return super.onTouchEvent(event)
                }

                if (!isRecording || mTime < min_record_time) {  // prepared没有完毕 或 录音时间过短
                    isRecording = false
                    mAudioRecordManager?.cancel()
                    // 用户录音时间太短，取消
                    if (mListener != null) {
                        MediaPlayer.create(mCtx, R.raw.fy).start()
                        mListener!!.onCancel(true)
                    }
                } else if (STATE_RECORDING == mCurState) { // 正常录制结束
                    mAudioRecordManager?.release()
                    if (mListener != null) {
                        MediaPlayer.create(mCtx, R.raw.gj).start()
                        mListener!!.onFinish(mTime, mAudioRecordManager?.currentFilePath!!)
                    }
                } else if (STATE_WANT_TO_CANCEL == mCurState) {
                    mAudioRecordManager?.cancel()
                    if (mListener != null) {
                        MediaPlayer.create(mCtx, R.raw.fy).start()
                        mListener!!.onCancel(false)
                    }
                }
                changeState(STATE_NORMAL)
                reset()
            }
        }

        return super.onTouchEvent(event)
    }

    /**
     * 恢复状态及标志位
     */
    private fun reset() {
        isRecording = false
        mReady = false
        mTime = 0f
        mCurState = STATE_NORMAL
    }

    /**
     * 根据坐标去判断是否应该取消
     *
     * @param x
     * @param y
     * @return
     */
    private fun wantToCancel(x: Int, y: Int): Boolean {
        if (x < 0 || x > width) {
            return true
        }
        return y < -DISTANCE_CANCEL || y > height + DISTANCE_CANCEL
    }

    /**
     * 按钮状态改变
     *
     * @param state
     */
    private fun changeState(state: Int) {
        if (mCurState != state) {
            mCurState = state
            when (mCurState) {
                STATE_NORMAL -> {
                    setBackgroundResource(bg_recorder_normal)
                    text = str_recorder_normal
                }
                STATE_RECORDING -> {
                    setBackgroundResource(bg_recorder_recording)
                    text = str_recorder_recording
                }
                STATE_WANT_TO_CANCEL -> {
                    setBackgroundResource(bg_recorder_cancel)
                    text = str_recorder_want_cancel
                }
            }
        }
    }

    companion object {

        private val TAG = "AudioRecorderButton"

        private val DISTANCE_CANCEL = 50
        private val STATE_NORMAL = 1
        private val STATE_RECORDING = 2
        private val STATE_WANT_TO_CANCEL = 3

        private val MSG_AUDIO_PREPARED = 0x110
        private val MSG_VOICE_CHANGE = 0x111
        private val MSG_UPDATE_TIME = 0x113
        private val MSG_TIME_LIMIT = 0x114
    }


}
