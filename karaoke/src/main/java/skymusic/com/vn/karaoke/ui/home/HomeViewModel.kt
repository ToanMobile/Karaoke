package skymusic.com.vn.karaoke.ui.home

import android.arch.lifecycle.LifecycleObserver
import android.content.Context
import android.content.Intent
import com.toan_itc.core.base.BaseViewModel
import skymusic.com.vn.karaoke.data.worker.CheckSongWorker
import skymusic.com.vn.karaoke.service.RecoderService
import javax.inject.Inject

class HomeViewModel
@Inject
internal constructor(checkSongWorker: CheckSongWorker): BaseViewModel(), LifecycleObserver {

    fun startService(context: Context?){
        val intentSync = Intent(context, RecoderService::class.java)
        intentSync.action = RecoderService.START_LITERNER
        context?.startService(intentSync)
    }

    fun stopService(context: Context?){
        val intentSync = Intent(context, RecoderService::class.java)
        intentSync.action = RecoderService.STOP_LITERNER
        context?.startService(intentSync)
    }
}
