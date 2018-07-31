package skymusic.com.vn.karaoke.di.module

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.toan_itc.core.architecture.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import skymusic.com.vn.karaoke.BuildConfig
import skymusic.com.vn.karaoke.data.service.ApiService
import skymusic.com.vn.karaoke.data.service.LogApiService
import skymusic.com.vn.karaoke.di.LogUrl
import skymusic.com.vn.karaoke.di.MainUrl
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 */
@Suppress("unused")
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.retryOnConnectionFailure(true)
        client.addInterceptor(LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .tag("LoggingI")
                .request("request")
                .response("response")
                .build())
        return client.build()
    }

    @Singleton
    @Provides
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    @Singleton
    @Provides
    fun sRestMainClient(@MainUrl retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun sRestUpdateClient(@LogUrl retrofit: Retrofit): LogApiService = retrofit.create(LogApiService::class.java)

    @Singleton
    @Provides
    @MainUrl
    fun provideMainRetrofit(client: OkHttpClient): Retrofit = createRetrofit(client, BuildConfig.BASE_URL)

    @Singleton
    @Provides
    @LogUrl
    fun provideLogRetrofit(client: OkHttpClient): Retrofit = createRetrofit(client, BuildConfig.BASE_URL_LOG)

    @Singleton
    @Provides
    @Named("Retrofit")
    fun createRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}
