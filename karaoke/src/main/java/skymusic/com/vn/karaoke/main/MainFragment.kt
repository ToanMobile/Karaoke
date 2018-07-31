package skymusic.com.vn.karaoke.main

import android.content.Context
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.FileUtils
import com.orhanobut.logger.Logger
import com.toan_itc.core.architecture.Status
import com.toan_itc.core.architecture.autoCleared
import com.toan_itc.core.architecture.observer
import com.toan_itc.core.base.CoreBaseDataFragment
import com.toan_itc.core.richutils.tryCatch
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*
import skymusic.com.vn.karaoke.R
import skymusic.com.vn.karaoke.binding.FragmentDataBindingComponent
import skymusic.com.vn.karaoke.databinding.FragmentMainBinding
import skymusic.com.vn.karaoke.utils.Base64
import java.io.File
import java.util.concurrent.TimeUnit
import android.media.AudioManager
import android.content.Context.AUDIO_SERVICE



class MainFragment : CoreBaseDataFragment<MainViewModel>() {
    private var songID: Int = 0
    private var countResult: Int = 0
    private var logID: Int = 0
    private var disposables: Disposable? = null
    private var mediaRecorder: MediaRecorder? = null
    private var pathRecord = ""
    private var binding by autoCleared<FragmentMainBinding>()
    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentMainBinding>(
                inflater,
                setLayoutResourceID(),
                container,
                false,
                dataBindingComponent
        )
        binding = dataBinding
        return dataBinding.root
    }

    override fun getViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun setLayoutResourceID(): Int = R.layout.fragment_main

    override fun initData() {
        FileUtils.deleteAllInDir(Environment.getExternalStorageDirectory().path + "/Karaoke/")
        initAudio()
        initRecord()
    }

    override fun initView() {
        waveLineView.startAnim()
    }

    private fun reloadService() {
        stopRecording()
        progressRecord(true)
    }

    private fun showDetails(isHaveSong: Boolean, songKey: Int = 0, title: String = "", singer: String = "", artist: String = "", copyright: Boolean = false, relatedRight: Boolean = false, vcpmcRight: Boolean = false) {
        countResult = 0
        if (waveLineView.isRunning) {
            waveLineView.stopAnim()
            waveLineView.onPause()
            waveLineView.isGone = true
        }
        if (isHaveSong) {
            if (group.isGone) {
                txtNoSong.isGone = true
                group.isVisible = true
            }
            txtSongName.text = title
            txtSongSinger.text = singer
            txtSongArtist.text = artist
            if (copyright || relatedRight) {
                txtCopyRightSky.text = getString(R.string.copyright_yes)
            }
            if (vcpmcRight) {
                txtCopyRightVCPMC.text = getString(R.string.copyright_yes)
            }
            if (logID != songKey) {
                logID = songKey
                viewModel.sendLog(context!!, songKey, title, singer, artist, copyright, relatedRight, vcpmcRight)
            }
        } else {
            if (group.isVisible)
                group.isGone = true
            txtNoSong.isVisible = true
        }
    }

    override fun onStart() {
        super.onStart()
        context?.apply {
            progressRecord(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseRecording()
    }

    private fun initAudio(){
        context?.apply {
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.mode = AudioManager.MODE_CURRENT
            audioManager.setParameters("noise_suppression=on")
        }
    }

    private fun initRecord() {
        tryCatch {
            generatePath()
            mediaRecorder = MediaRecorder()
            mediaRecorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioSamplingRate(44100)
                setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC)
                setAudioEncodingBitRate(48000)
                //mRecorder.setAudioSamplingRate(16000);
                setOutputFile(pathRecord)
                //setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                prepare()
            }
        }
    }

    private fun generatePath(): String {
        pathRecord = Environment.getExternalStorageDirectory().path + "/Karaoke/" + System.currentTimeMillis() + "_Record.mp3"
        if (!FileUtils.isFileExists(pathRecord)) FileUtils.createOrExistsFile(pathRecord)
        //Logger.e("initRecord:Path=$pathRecord")
        return pathRecord
    }

    private fun progressRecord(isReload : Boolean = false){
       // Logger.e("progressRecord:$isReload")
        startRecording(isReload)
        //pathRecord =  Environment.getExternalStorageDirectory().path + "/Karaoke/Record.mp3"
        disposables?.dispose()
        disposables = Flowable.timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .filter {
                    FileUtils.isFileExists(pathRecord)
                }
                .map {
                    return@map decode(pathRecord)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    checkSong(it.replace("\n", "").replace("\r", "").trim())
                }
    }

    private fun startRecording(isReload: Boolean) {
        if(isReload) {
            releaseRecording()
            initRecord()
            mediaRecorder?.apply {
                try {
                    start()
                } catch (stopException: RuntimeException) {}
            }
            return
        }
        mediaRecorder?.apply {
            try {
                start()
            } catch (stopException: RuntimeException) {}
        }
    }

    @Throws(Exception::class)
    private fun decode(sourceFile: String): String {
        val bytes = File(sourceFile).readBytes()
        return Base64.encode(bytes)
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            try {
                stop()
            } catch (stopException: RuntimeException) {
                //handle cleanup here
            }
        }
    }

    private fun releaseRecording() {
        mediaRecorder?.apply {
            try {
                stop()
                release()
            } catch (stopException: RuntimeException) {
                //handle cleanup here
            }
        }
        mediaRecorder = null
    }

    private fun checkSong(base64: String) {
        if (base64.isNotEmpty()) {
            viewModel.getSong(base64).observer(this@MainFragment) {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.apply {
                            /*if (songID != song_id) {
                                Logger.e("checkSong=" + it.data.toString())
                                songID = song_id
                            } else {
                                countResult++
                                Logger.e("songID trung nhau:$countResult")
                                if (countResult > 2) {
                                    showDetails(true, song_id, song_name, song_artist, song_artist, false, false, false)
                                }
                            }*/
                            //TODO:TEST
                            showDetails(true, song_id, song_name, confidence.toString(), song_artist, false, false, false)
                        }
                    }
                    Status.ERROR -> {
                        songID = 0
                        countResult++
                        Logger.e("Load data error$countResult")
                        if (countResult > 2) {
                            showDetails(false)
                        }
                    }
                    Status.LOADING -> {
                        //Logger.e("LOADING")
                    }
                }
            }
        } else {
            showDetails(false)
        }
        reloadService()
    }
}
