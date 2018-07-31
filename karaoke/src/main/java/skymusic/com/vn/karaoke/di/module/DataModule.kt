package skymusic.com.vn.karaoke.di.module

import com.toan_itc.core.architecture.AppExecutors
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 */
@Suppress("unused")
@Module
class DataModule {

    @Singleton
    @Provides
    internal fun appExecutors(): AppExecutors {
        return AppExecutors()
    }
}
