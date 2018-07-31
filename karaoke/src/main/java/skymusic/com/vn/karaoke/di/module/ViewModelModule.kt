package skymusic.com.vn.karaoke.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import skymusic.com.vn.karaoke.di.ViewModelFactory
import skymusic.com.vn.karaoke.di.key.ViewModelKey
import skymusic.com.vn.karaoke.main.MainViewModel

/**
 * Created by ToanDev on 28/05/18.
 * Email:Huynhvantoan.itc@gmail.com
 */

@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
