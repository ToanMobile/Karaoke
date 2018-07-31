package skymusic.com.vn.karaoke.data.repository

import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.toan_itc.core.architecture.AppExecutors
import com.toan_itc.core.architecture.Resource
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import skymusic.com.vn.karaoke.data.model.GetSong
import skymusic.com.vn.karaoke.data.model.PlayEnt
import skymusic.com.vn.karaoke.data.service.ApiService
import skymusic.com.vn.karaoke.data.service.LogApiService
import skymusic.com.vn.karaoke.data.task.FetchGetSongTask
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CheckSongRepository
@Inject
constructor(private val apiService: ApiService, private val sendLogApiService: LogApiService, private val appExecutors: AppExecutors) {

    fun getSong(base64: String): LiveData<Resource<GetSong>> {
        val fetchGetSongTask = FetchGetSongTask(base64,apiService)
        appExecutors.networkIO().execute(fetchGetSongTask)
        return fetchGetSongTask.getLiveData()
    }

    fun sendLog(playEnt: PlayEnt) {
       // val data = Gson().toJson(playEnt)
        sendLogApiService.sendLog(Gson().toJson(playEnt)).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                t?.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                Logger.d("onResponse=" + response?.body().toString())
            }

        })
    }
}