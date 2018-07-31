/*
package skymusic.com.vn.karaoke.service

import android.app.IntentService
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import java.util.Base64
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.LogUtils
import com.orhanobut.logger.Logger
import com.toan_itc.core.richutils.tryCatch
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import skymusic.com.vn.karaoke.define.Base64Event
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.TimeUnit

class RecoderService : IntentService("RecoderService"){

    companion object {
        const val INIT_RECORD = "INIT_RECORD"
        const val START_RECORD = "START_RECORD"
        const val STOP_RECORD = "STOP_RECORD"
    }

    override fun onHandleIntent(intent: Intent) {
        Logger.e("RecoderService:onHandleIntent=" + intent.action)
        intent.apply {
            when (action) {
                INIT_RECORD ->  initRecord()
                START_RECORD -> startRecording()
                STOP_RECORD -> stopRecording()
            }
        }
    }

    private fun initRecord(){
        tryCatch {
           */
/* mediaRecorder = MediaRecorder()
            if(!FileUtils.isFileExists(pathRecord)) FileUtils.createFileByDeleteOldFile(pathRecord)
            Logger.e("initRecord:Path=$pathRecord")
            mediaRecorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
                setOutputFile(pathRecord)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                prepare()
            }*//*

        }
    }

    fun listFiles(): MutableList<String> {
        val file = File(filesDir.absolutePath)
        return if (file.exists() && file.isDirectory) {
            file.list().toMutableList()
        } else {
            mutableListOf()
        }
    }

    private fun generateFilename(): String = "${this.filesDir.absolutePath}/${System.currentTimeMillis()}.aac"

    private fun startRecording() {
        mediaRecorder?.start()
        disposables?.dispose()
        disposables = Flowable.timer(2,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .map {
                    return@map decode(pathRecord)
                }
                .map {
                    Log.e("decode=",it)
                    EventBus.getDefault().post(Base64Event(it))
                }
                .subscribe()
    }

    @Throws(Exception::class)
    private fun decode(sourceFile: String) :String {
        stopRecording()
        //val decode = encodeFileToBase64Binary(FileUtils.getFileByPath(sourceFile))

        //val base64 = skymusic.com.vn.karaoke.utils.Base64.encode(bytes)

        val bytes = File(sourceFile).readBytes()
        val base64 = skymusic.com.vn.karaoke.utils.Base64.encode(bytes)
        return base64
    }

    */
/*@Throws(Exception::class)
    private fun encodeFileToBase64Binary(file: File): String {
        val fileInputStreamReader = FileInputStream(file)
        val bytes = ByteArray(file.length().toInt())
        fileInputStreamReader.read(bytes)
        return String(Base64.encodeBase64(bytes), charset("UTF-8"))
    }*//*


    private fun stopRecording(){
        mediaRecorder?.apply {
            stop()
            release()
        }
    }

    private fun releaseRecording(){
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }

    override fun onDestroy() {
        super.onDestroy()
        //releaseRecording()
        //stopSelf()
    }
}*/
