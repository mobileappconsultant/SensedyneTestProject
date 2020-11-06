package com.android.sensyneapplication.di.module

import com.android.sensyneapplication.BuildConfig
import com.android.sensyneapplication.domain.database_search.QueryBuilder
import com.android.sensyneapplication.domain.database_search.SearchEntryRegexProcessor
import com.android.sensyneapplication.domain.mapper.HospitalResponseModelToRoomItemMapper
import com.android.sensyneapplication.domain.mapper.RoomResponseToHospitalItemMapper
import com.android.sensyneapplication.domain.remote.HospitalQueryService
import com.android.sensyneapplication.framework.domain.model.HospitalItem
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class HospitalApiModule {

    private val BASE_URL = "https://draysontechnologies.github.io/"

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
    fun providesHospitalApiClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
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
    fun providesRetrofit(moshi: Moshi, hospitalApiClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(hospitalApiClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
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
    fun providesQueryBuilder() = QueryBuilder(SearchEntryRegexProcessor())

    @Provides
    @Singleton
    fun provideRoomResponseMapper() = RoomResponseToHospitalItemMapper()
}
