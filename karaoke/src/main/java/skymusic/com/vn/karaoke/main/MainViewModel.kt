package skymusic.com.vn.karaoke.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.NetworkUtils
import com.toan_itc.core.architecture.Resource
import com.toan_itc.core.base.BaseViewModel
import skymusic.com.vn.karaoke.data.model.GetSong
import skymusic.com.vn.karaoke.data.model.PlayEnt
import skymusic.com.vn.karaoke.data.repository.CheckSongRepository
import skymusic.com.vn.karaoke.utils.getDeviceID
import javax.inject.Inject

class MainViewModel
@Inject
internal constructor(private val checkSongRepository: CheckSongRepository) : BaseViewModel(), LifecycleObserver {

    fun getSong(base64: String): LiveData<Resource<GetSong>> = checkSongRepository.getSong(base64)

    @SuppressLint("MissingPermission")
    fun sendLog(context: Context, songKey: Int, title: String, songSinger: String, songArtist: String, songCopyright: Boolean, songRelatedRight: Boolean , songVcpmcRight: Boolean) {
        val playEnt = PlayEnt()
        with(playEnt) {
            song_name = title
            singer = songSinger
            artist = songArtist
            sky_key = songKey.toString()
            timestamp = System.currentTimeMillis()
            ip = NetworkUtils.getIPAddress(true)
            device_id = getDeviceID(context)
            app_version = AppUtils.getAppVersionName()
            copyRight = songCopyright
            relatedRight = songRelatedRight
            vcpmcRight = songVcpmcRight
        }
        checkSongRepository.sendLog(playEnt)
    }
/*

    fun startService(context: Context?) {
        val intentSync = Intent(context, RecoderService::class.java)
        intentSync.action = RecoderService.START_RECORD
        context?.startService(intentSync)
    }

    fun stopService(context: Context?) {
        val intentSync = Intent(context, RecoderService::class.java)
        intentSync.action = RecoderService.STOP_RECORD
        context?.startService(intentSync)
    }
*/

}
