package com.wk.mediate.data.di

import com.wk.mediate.present.config.Pref
import com.wk.mediate.data.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val baseUrl = "http://3.37.10.226/"

    @Singleton
    @Provides
    @Named("provideRetrofit")
    fun provideRetrofit() : Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    @Named("provideOAuthRetrofit")
    fun provideOAuthRetrofit() : Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder().addHeader("Authorization", "${Pref.auth?.accessToken}").build()
                chain.proceed(request)
            }.build())
            .build()

    @Singleton
    @Provides
    @Named("provideSchoolRetrofit")
    fun provideSchoolRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl("https://open.neis.go.kr/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    @Named("provideAreaRetrofit")
    fun provideAreaRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl("https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    @Named("provideUnivRetrofit")
    fun provideUnivRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl("https://www.career.go.kr/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}