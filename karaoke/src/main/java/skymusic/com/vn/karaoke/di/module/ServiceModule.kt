package skymusic.com.vn.karaoke.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import skymusic.com.vn.karaoke.di.ServiceScope
import skymusic.com.vn.karaoke.service.RecoderService


/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 */
@Suppress("unused")
@Module
abstract class ServiceModule {

    @ServiceScope
    @ContributesAndroidInjector
    abstract fun bindRecoderService(): RecoderService

}