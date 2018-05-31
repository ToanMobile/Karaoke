package skymusic.com.vn.karaoke.app

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ProcessLifecycleOwner
import android.os.StrictMode
import android.support.v4.app.Fragment
import com.blankj.utilcode.util.Utils
import com.github.moduth.blockcanary.BlockCanary
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.DaggerApplication
import dagger.android.support.HasSupportFragmentInjector
import skymusic.com.vn.karaoke.BuildConfig
import skymusic.com.vn.karaoke.R
import skymusic.com.vn.karaoke.di.applyAutoInjector
import skymusic.com.vn.karaoke.di.component.DaggerAppComponent
import javax.inject.Inject

/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 */

class App : DaggerApplication() {

    private var mRefWatcher: RefWatcher? = null
    private lateinit var appObserver: ForegroundBackgroundListener

    companion object {
        lateinit var instance: App
            private set
    }

    fun getRefWatcher(): RefWatcher? {
        return mRefWatcher
    }

    override fun applicationInjector() = DaggerAppComponent.builder()
            .application(this)
            .build()

    override fun onCreate() {
        super.onCreate()
        applyAutoInjector()
        if (BuildConfig.DEBUG) {
            strictMode()
            setupLogger()
            setupTest()
        }
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
    }

    private fun setupTest() {
        if (LeakCanary.isInAnalyzerProcess(this)) return
        mRefWatcher = LeakCanary.install(this)
        BlockCanary.install(this, AppBlockCanaryContext()).start()
    }

}
