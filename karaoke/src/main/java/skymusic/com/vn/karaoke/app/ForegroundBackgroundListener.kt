package skymusic.com.vn.karaoke.app

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.orhanobut.logger.Logger

/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 */

class ForegroundBackgroundListener : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun createSomething() {
        Logger.d("ProcessLog"+ "Lifecycle.Event.ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startSomething() {
        Logger.d("ProcessLog"+ "Lifecycle.Event.ON_START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopSomething() {
        Logger.d("ProcessLog"+ "Lifecycle.Event.ON_STOP")
    }
}