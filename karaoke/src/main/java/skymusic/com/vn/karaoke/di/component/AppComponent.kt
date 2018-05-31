package skymusic.com.vn.karaoke.di.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import skymusic.com.vn.karaoke.app.App
import skymusic.com.vn.karaoke.di.module.*
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
    (ServiceModule::class),
    (ActivityBuildersModule::class),
    (ViewModelModule::class)])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)
}
