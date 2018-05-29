package skymusic.com.vn.karaoke.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import skymusic.com.vn.karaoke.di.FragmentScope
import skymusic.com.vn.karaoke.ui.details.DetailsFragment
import skymusic.com.vn.karaoke.ui.home.HomeFragment

/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 */
@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment(): DetailsFragment

}
