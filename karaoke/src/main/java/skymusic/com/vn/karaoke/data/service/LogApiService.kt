package skymusic.com.vn.karaoke.data.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 * REST API access points
 */

interface LogApiService {

    @FormUrlEncoded
    @POST("tracking/play")
    fun sendLog(@Field("data") data: String): Call<ResponseBody>

}
