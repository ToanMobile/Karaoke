package skymusic.com.vn.karaoke.di

import javax.inject.Scope

/**
 * Created by ToanDev on 08/03/18.
 * Email:Huynhvantoan.itc@gmail.com
 */

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FragmentScope

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ServiceScope