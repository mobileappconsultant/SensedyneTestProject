package com.android.sensyneapplication

import com.android.sensyneapplication.domain.model.HospitalResponseModelToRoomItemMapper
import com.android.sensyneapplication.domain.model.RoomResponseToHospitalItemMapper
import com.android.sensyneapplication.framework.domain.HospitalQueryService
import com.android.sensyneapplication.framework.domain.model.HospitalItem
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zavamed.makeitfree.TestSensyneApplication
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class TestHospitalApiModule(var application: TestSensyneApplication) {

    @Provides
    @Singleton
    fun providesMockWebServer(): MockWebServer {
        var mockWebServer: MockWebServer? = null
        val thread = Thread({
            mockWebServer = MockWebServer()
            mockWebServer?.dispatcher = MockServerDispatcher(application).RequestDispatcher()
        })

        thread.start()
        thread.join()

        return mockWebServer ?: throw NullPointerException()
    }

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(mockWebServer: MockWebServer): String {
        var url = ""
        val t = Thread({
            url = mockWebServer.url("/").toString()
        })
        t.start()
        t.join()

        return url
    }

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            } else {
                level = HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun providesTestHospitalApiClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory())
            .add(HospitalItem.HospitalItemAdapter())
            .build()
    }

    @Provides
    @Singleton
    fun providesTestRetrofit(@Named("baseUrl") baseUrl: String, moshi: Moshi, hospitalApiClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(hospitalApiClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun providesHospitalApi(retrofit: Retrofit): HospitalQueryService {
        return retrofit.create(HospitalQueryService::class.java)
    }

    @Provides
    @Singleton
    fun provideHospitalReponseMapper() = HospitalResponseModelToRoomItemMapper()

    @Provides
    @Singleton
    fun provideRoomResponseMapper() = RoomResponseToHospitalItemMapper()
}
