package skymusic.com.vn.karaoke.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import skymusic.com.vn.karaoke.MainActivity
import skymusic.com.vn.karaoke.di.ActivityScope

/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 */
@Suppress("unused")
@Module
abstract class ActivityBuildersModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [(FragmentBuildersModule::class)])
    abstract fun contributeMainActivity(): MainActivity

}
