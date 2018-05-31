package skymusic.com.vn.karaoke.service

import android.app.IntentService
import android.content.Intent
import android.os.Environment
import com.acrcloud.rec.sdk.ACRCloudClient
import com.acrcloud.rec.sdk.ACRCloudConfig
import com.acrcloud.rec.sdk.IACRCloudListener
import com.orhanobut.logger.Logger
import org.json.JSONException
import org.json.JSONObject
import skymusic.com.vn.karaoke.BuildConfig
import java.io.File
import javax.inject.Singleton


@Singleton
class RecoderService : IntentService("RecoderService"), IACRCloudListener {

    private var mClient: ACRCloudClient? = null
    private var mConfig: ACRCloudConfig? = null
    private var mProcessing = false
    private var initState = false
    private var path = ""
    private var isRecording = false

    companion object {
        const val START_LITERNER = "START_LITERNER"
        const val STOP_LITERNER = "STOP_LITERNER"
    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.apply {
            Logger.d("SyncService:onHandleIntent=$action")
            when (action) {
                START_LITERNER -> {
                    initACRCloud()
                    if (!mProcessing) {
                        mProcessing = mClient != null && mClient!!.startRecognize()
                    }
                }
                STOP_LITERNER -> {
                }
            }
        }
    }

    private fun initACRCloud() {
        path = (Environment.getExternalStorageDirectory().toString() + "/acrcloud/model")
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        mConfig = ACRCloudConfig()
        mConfig?.apply {
            acrcloudListener = this@RecoderService
            context = this@RecoderService.applicationContext
            host = BuildConfig.HOST
            dbPath = path // offline db path, you can change it with other path which this app can access.
            accessKey = BuildConfig.ACCESS_KEY
            accessSecret = BuildConfig.ACCESS_SECRET
            protocol = ACRCloudConfig.ACRCloudNetworkProtocol.PROTOCOL_HTTP // PROTOCOL_HTTPS
            reqMode = ACRCloudConfig.ACRCloudRecMode.REC_MODE_REMOTE
            mClient = ACRCloudClient()
            mClient?.let { mClient ->
                // If reqMode is REC_MODE_LOCAL or REC_MODE_BOTH,
                // the function initWithConfig is used to load offline db, and it may cost long time.
                initState = mClient.initWithConfig(this)
                if (initState) {
                    mClient.startPreRecord(3000); //start prerecord, you can call "this.mClient.stopPreRecord()" to stop prerecord.
                }
            }
        }
    }


    override fun onResult(result: String?) {
        mClient?.apply {
            cancel()
            mProcessing = false
        }
        var title = ""
        var singer = ""
        try {
            val j = JSONObject(result)
            val j1 = j.getJSONObject("status")
            val j2 = j1.getInt("code")
            if (j2 == 0) {
                val metadata = j.getJSONObject("metadata")
                if (metadata.has("humming")) {
                    val hummings = metadata.getJSONArray("humming")
                    for (i in 0 until hummings.length()) {
                        val tt = hummings.get(i) as JSONObject
                        title = tt.getString("title")
                        singer = tt.getString("Singer")
                    }
                }
                if (metadata.has("music")) {
                    val musics = metadata.getJSONArray("music")
                    for (i in 0 until musics.length()) {
                        val tt = musics.get(i) as JSONObject
                        title = tt.getString("title")
                        singer = tt.getString("Singer")
                    }
                }
                if (metadata.has("streams")) {
                    val musics = metadata.getJSONArray("streams")
                    for (i in 0 until musics.length()) {
                        val tt = musics.get(i) as JSONObject
                        title = tt.getString("title")
                        val channelId = tt.getString("channel_id")
                        //tres = tres + (i + 1) + ".  Title: " + title + "    Channel Id: " + channelId + "\n";
                    }
                }
                if (metadata.has("custom_files")) {
                    val musics = metadata.getJSONArray("custom_files")
                    for (i in 0 until musics.length()) {
                        val tt = musics.get(i) as JSONObject
                        title = tt.getString("title")
                        singer = tt.getString("Singer")
                    }
                }
            }
            Logger.e("result=$result")
            checkSong(title, singer)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onVolumeChanged(volume: Double) {

    }

    private fun checkSong(title: String, singer: String) {

    }
}