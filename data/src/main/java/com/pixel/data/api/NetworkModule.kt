package com.pixel.data.api

import android.util.Log
import com.pixel.data.api.webServices.ProductWebServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor =
            HttpLoggingInterceptor {
                Log.e("retrofit", "$it")
            }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    fun provideGson(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideRetrofit(
        okhttp: OkHttpClient,
        gson: GsonConverterFactory,
    ): Retrofit =
        Retrofit
            .Builder()
            .client(okhttp)
            .addConverterFactory(gson)
            .baseUrl("https://dummyjson.com")
            .build()

    @Provides
    fun provideProductWebServices(retrofit: Retrofit): ProductWebServices =
        retrofit.create(
            ProductWebServices::class.java,
        )
}
