package skymusic.com.vn.karaoke.data.service

import androidx.lifecycle.LiveData
import com.toan_itc.core.architecture.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query
import skymusic.com.vn.karaoke.data.model.CheckSong


/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 * REST API access points
 */

interface ApiService {

    @GET("checksong")
    fun checkSong(@Query("songName") songName: String, @Query("singer") singer: String, @Query("op") op: String
                  , @Query("client_id") client_id: String, @Query("client_secret") client_secret: String): LiveData<ApiResponse<JsonArray<CheckSong>>>

}
