package skymusic.com.vn.karaoke.data.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.toan_itc.core.architecture.*
import skymusic.com.vn.karaoke.data.model.GetSong
import skymusic.com.vn.karaoke.data.service.ApiService
import skymusic.com.vn.karaoke.utils.returnBody

/**
 * Created by ToanDev on 04/06/18.
 * Email:Huynhvantoan.itc@gmail.com
 */

class FetchGetSongTask
internal constructor(private val base64Record: String, private val apiService: ApiService) : Runnable {
    private val liveData = MutableLiveData<Resource<GetSong>>()
    private val messageError = "This content is currently unavailable"

    override fun run() {
        liveData.postValue(Resource.loading(null))
        val newValue = try {
            //Logger.e("FetchGetSongTask=$value")
            val response = apiService.recognize(returnBody("{\"data\":\"$base64Record\"}")).execute()
            val apiResponse = ApiResponse.create(response)
            when (apiResponse) {
                is ApiSuccessResponse -> Resource.success(response.body()?.data)
                is ApiEmptyResponse -> Resource.error(messageError, null)
                is ApiErrorResponse -> Resource.error(apiResponse.errorMessage, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(e.message.toString(), null)
        }
        liveData.postValue(newValue)
    }

    internal fun getLiveData(): LiveData<Resource<GetSong>> {
        return liveData
    }

}
