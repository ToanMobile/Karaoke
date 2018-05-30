package skymusic.com.vn.karaoke.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import skymusic.com.vn.karaoke.di.ViewModelFactory
import skymusic.com.vn.karaoke.di.key.ViewModelKey
import skymusic.com.vn.karaoke.ui.details.DetailsViewModel
import skymusic.com.vn.karaoke.ui.home.HomeViewModel

/**
 * Created by ToanDev on 28/05/18.
 * Email:Huynhvantoan.itc@gmail.com
 */

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
