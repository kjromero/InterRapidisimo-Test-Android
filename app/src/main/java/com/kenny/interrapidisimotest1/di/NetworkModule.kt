package com.kenny.interrapidisimotest1.di

import com.kenny.interrapidisimotest1.data.remote.api.FrameworkApi
import com.kenny.interrapidisimotest1.data.remote.api.SecurityApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

    @Provides
    @Singleton
    @Named("framework")
    fun provideFrameworkRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://apitesting.interrapidisimo.co/apicontrollerpruebas/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named("security")
    fun provideSecurityRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://apitesting.interrapidisimo.co/FtEntregaElectronica/MultiCanales/ApiSeguridadPruebas/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideFrameworkApi(@Named("framework") retrofit: Retrofit): FrameworkApi =
        retrofit.create(FrameworkApi::class.java)

    @Provides
    @Singleton
    fun provideSecurityApi(@Named("security") retrofit: Retrofit): SecurityApi =
        retrofit.create(SecurityApi::class.java)
}