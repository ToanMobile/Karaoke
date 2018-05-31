package skymusic.com.vn.karaoke.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.toan_itc.core.architecture.ApiResponse
import com.toan_itc.core.architecture.AppExecutors
import com.toan_itc.core.architecture.NetworkBoundResource
import com.toan_itc.core.architecture.Resource
import skymusic.com.vn.karaoke.data.model.CheckSong
import skymusic.com.vn.karaoke.data.service.ApiService
import skymusic.com.vn.karaoke.data.service.JsonArray
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckSongRepository
@Inject
constructor(private val apiService: ApiService, private val appExecutors: AppExecutors) {

    fun checkSong(songName: String, singer: String, op: String, clientID: String, clientSecret: String): LiveData<Resource<CheckSong>> {
        return object : NetworkBoundResource<CheckSong, CheckSong>(appExecutors) {
            override fun saveCallResult(item: CheckSong) {}

            override fun shouldFetch(data: CheckSong?) = data == null

            override fun loadFromDb() = MutableLiveData<CheckSong>()

            override fun createCall() = apiService.checkSong(songName, singer, op, clientID, clientSecret)
        }.asLiveData()
    }

}