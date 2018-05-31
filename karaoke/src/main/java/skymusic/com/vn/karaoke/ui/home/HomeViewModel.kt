package skymusic.com.vn.karaoke.ui.home

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.Transformations.switchMap
import android.content.Context
import android.content.Intent
import com.toan_itc.core.architecture.AbsentLiveData
import com.toan_itc.core.architecture.Resource
import com.toan_itc.core.base.BaseViewModel
import skymusic.com.vn.karaoke.data.model.CheckSong
import skymusic.com.vn.karaoke.data.repository.CheckSongRepository
import skymusic.com.vn.karaoke.data.worker.CheckSongWorker
import skymusic.com.vn.karaoke.service.RecoderService
import javax.inject.Inject

class HomeViewModel
@Inject
internal constructor(checkSongRepository: CheckSongRepository) : BaseViewModel(), LifecycleObserver {
    private val _song: MutableLiveData<CheckSongId> = MutableLiveData()

    val checkSong: LiveData<Resource<CheckSong>> = switchMap(_song) { input ->
        input.ifExists { songName, singer, op, clientID, clientSecret ->
            checkSongRepository.checkSong(songName, singer, op, clientID, clientSecret)
        }
    }

    fun setInfoSong(songName: String?, singer: String?, op: String?, clientID: String?, clientSecret: String?) {
        val update = CheckSongId(songName, singer, op, clientID, clientSecret)
        if (_song.value == update) {
            return
        }
        _song.value = update
    }

    data class CheckSongId(val songName: String?, val singer: String?, val op: String?, val clientID: String?, val clientSecret: String?) {
        fun <T> ifExists(f: (String, String, String, String, String) -> LiveData<T>): LiveData<T> {
            return if (songName.isNullOrBlank() || singer.isNullOrBlank() || op.isNullOrBlank() || clientID.isNullOrBlank() || clientSecret.isNullOrBlank()) {
                AbsentLiveData.create()
            } else {
                f(songName!!, singer!!, op!!, clientID!!, clientSecret!!)
            }
        }
    }

    fun startService(context: Context?) {
        val intentSync = Intent(context, RecoderService::class.java)
        intentSync.action = RecoderService.START_LITERNER
        context?.startService(intentSync)
    }

    fun stopService(context: Context?) {
        val intentSync = Intent(context, RecoderService::class.java)
        intentSync.action = RecoderService.STOP_LITERNER
        context?.startService(intentSync)
    }

}
