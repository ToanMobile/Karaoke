package skymusic.com.vn.karaoke.data.service

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import skymusic.com.vn.karaoke.data.model.GetSong

/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 * REST API access points
 */

interface ApiService {

    @Headers("api-key: " + "7d13cea1d955c89306c405cf9adb4859c5a1fd3f","Content-Type: " + "application/json; charset=utf-8")
    @POST("recognize")
    fun recognize(@Body body: RequestBody): Call<JsonObject<GetSong>>

}
