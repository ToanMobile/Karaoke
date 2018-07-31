package skymusic.com.vn.karaoke.app

import android.app.Activity
import android.app.Application
import android.net.TrafficStats
import android.os.StrictMode
import androidx.lifecycle.ProcessLifecycleOwner
import com.blankj.utilcode.util.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import skymusic.com.vn.karaoke.BuildConfig
import skymusic.com.vn.karaoke.R
import skymusic.com.vn.karaoke.di.AppInjector
import javax.inject.Inject

/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 */

class App : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    override fun activityInjector() = dispatchingAndroidInjector
    private lateinit var appObserver: ForegroundBackgroundListener

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        setupLogger()
        setupData()
    }

    private fun strictMode() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectAll()
                .build())
    }

    private fun setupLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(getString(R.string.app_name))
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        ProcessLifecycleOwner.get()
                .lifecycle
                .addObserver(ForegroundBackgroundListener()
                        .also { appObserver = it })
    }

    private fun setupData() {
        Utils.init(this)
        TrafficStats.setThreadStatsTag(10000)
    }

}
