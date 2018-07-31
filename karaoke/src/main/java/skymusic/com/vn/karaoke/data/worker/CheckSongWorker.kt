package skymusic.com.vn.karaoke.data.worker

import androidx.work.Worker
import com.toan_itc.core.architecture.AppExecutors
import skymusic.com.vn.karaoke.data.service.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckSongWorker @Inject constructor(
        private val appExecutors: AppExecutors,
        private val checkSongApiService: ApiService
) : Worker(){

    override fun doWork(): Result {
        try{
            return Worker.Result.SUCCESS
        }
        catch (e:Exception){
            return Worker.Result.FAILURE
        }
    }
}