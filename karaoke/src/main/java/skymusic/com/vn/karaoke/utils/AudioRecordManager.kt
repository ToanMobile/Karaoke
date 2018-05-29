package skymusic.com.vn.karaoke.utils

import android.media.MediaRecorder
import android.util.Log
import java.io.File
import java.io.IOException
import java.util.*

/**
 * Created by 焕楠 2016-6-27
 */
class AudioRecordManager private constructor(private val mDir: String) {

    private var mMediaRecorder: MediaRecorder? = null
    var currentFilePath: String? = null
        private set

    private var isPrepared = false

    var mListener: AudioStateListener? = null

    /**
     * 回调准备完毕
     */
    interface AudioStateListener {
        fun wellPrepared()
    }

    fun setOnAudioStateListner(listner: AudioStateListener) {
        mListener = listner
    }

    fun prepareAudio() {
        Log.d("LONG", "preparedAudio")
        try {
            isPrepared = false
            val dir = File(mDir)
            if (!dir.exists()) {
                dir.mkdirs()
            }

            val fileName = generateFileName()
            val file = File(dir, fileName)

            Log.d("LONG", "the file name is $fileName")

            currentFilePath = file.absolutePath
            mMediaRecorder = MediaRecorder()
            // 设置输出文件
            mMediaRecorder!!.setOutputFile(file.absolutePath)
            Log.d("LONG", "1")
            // 设置MediaRecorder的音频源为麦克风
            mMediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            Log.d("LONG", "2")
            // 设置音频格式 AMR_NB
            //            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mMediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            Log.d("LONG", "3")
            // 设置音频的编码为AMR
            //            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            Log.d("LONG", "4")
            mMediaRecorder!!.prepare()
            Log.d("LONG", "5")
            mMediaRecorder!!.start()
            Log.d("LONG", "6")
            // 准备结束
            isPrepared = true
            Log.d("LONG", "7")

            if (mListener != null) {
                Log.d("LONG", "AudioStateListener is not null")
                mListener!!.wellPrepared()
            } else {
                Log.d("LONG", "lisetner null")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    /**
     * 随机生成文件的名称
     *
     * @return
     */
    private fun generateFileName(): String {
        //        return UUID.randomUUID().toString() + ".amr";
        return UUID.randomUUID().toString() + ".aac"
    }

    fun getVoiceLevel(maxLevel: Int): Int {
        if (isPrepared) {
            // mMediaRecorder.getMaxAmplitude() 1-32767
            try {
                return maxLevel * mMediaRecorder!!.maxAmplitude / 32768 + 1
            } catch (e: Exception) {
                // 忽略产生的异常
            }

        }
        return 1
    }

    fun release() {
        if (mMediaRecorder != null) {
            mMediaRecorder!!.stop()
            mMediaRecorder!!.release()
            mMediaRecorder = null
        }
    }

    /**
     * 释放资源 同时删除音频文件
     */
    fun cancel() {
        release()
        if (currentFilePath != null) {
            val file = File(currentFilePath!!)
            file.delete()
            currentFilePath = null
        }
    }

    companion object {

        private var mInstance: AudioRecordManager? = null

        fun getInstance(dir: String): AudioRecordManager? {
            if (null == mInstance) {
                synchronized(AudioRecordManager::class.java) {
                    if (null == mInstance) {
                        mInstance = AudioRecordManager(dir)
                    }
                }
            }
            return mInstance
        }
    }
}