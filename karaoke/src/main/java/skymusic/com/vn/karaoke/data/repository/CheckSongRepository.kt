package skymusic.com.vn.karaoke.data.repository

import android.arch.lifecycle.LiveData
import com.toan_itc.core.architecture.ApiResponse
import com.toan_itc.core.architecture.AppExecutors
import skymusic.com.vn.karaoke.data.model.CheckSong
import skymusic.com.vn.karaoke.data.service.ApiService
import skymusic.com.vn.karaoke.data.service.JsonArray
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckSongRepository
@Inject
constructor(private val apiService: ApiService, private val appExecutors: AppExecutors) {

    fun checkSong(songName: String, singer: String, op: String, clientID: String, clientSecret: String): LiveData<ApiResponse<JsonArray<CheckSong>>> {
        return apiService.checkSong(songName, singer, op, clientID, clientSecret)
    }

}