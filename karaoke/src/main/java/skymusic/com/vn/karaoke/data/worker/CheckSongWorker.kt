package skymusic.com.vn.karaoke.data.worker

import android.support.annotation.NonNull
import androidx.work.Worker
import com.toan_itc.core.architecture.AppExecutors
import skymusic.com.vn.karaoke.data.service.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckSongWorker @Inject constructor(
        private val appExecutors: AppExecutors,
        private val apiService: ApiService
) : Worker(){

    @Override
    @NonNull
    override fun doWork(): WorkerResult {
        try{
            return Worker.WorkerResult.SUCCESS
        }
        catch (e:Exception){
            return Worker.WorkerResult.FAILURE
        }
    }
}