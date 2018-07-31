package skymusic.com.vn.karaoke.di

import javax.inject.Qualifier

/**
 * Created by ToanDev on 08/03/18.
 * Email:Huynhvantoan.itc@gmail.com
 */

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
annotation class MainUrl

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
annotation class LogUrl
