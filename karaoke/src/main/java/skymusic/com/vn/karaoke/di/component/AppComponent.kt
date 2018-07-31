package skymusic.com.vn.karaoke.di.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import skymusic.com.vn.karaoke.app.App
import skymusic.com.vn.karaoke.di.module.ActivityBuildersModule
import skymusic.com.vn.karaoke.di.module.DataModule
import skymusic.com.vn.karaoke.di.module.NetworkModule
import skymusic.com.vn.karaoke.di.module.ViewModelModule
import javax.inject.Singleton

/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 */
@Suppress("unused")
@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class),
    (NetworkModule::class),
    (DataModule::class),
    (ActivityBuildersModule::class),
    (ViewModelModule::class)])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}
