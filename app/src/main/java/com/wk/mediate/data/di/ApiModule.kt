package com.wk.mediate.data.di

import com.wk.mediate.data.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    fun provideLoginAPIService(@Named("provideRetrofit") retrofit: Retrofit): LoginApi = retrofit.create(
        LoginApi::class.java)

    @Provides
    fun provideRegisterAPIService(@Named("provideRetrofit") retrofit: Retrofit): RegisterApi = retrofit.create(
        RegisterApi::class.java)

    @Provides
    fun provideSchoolService(@Named("provideSchoolRetrofit") retrofit: Retrofit): SchoolInfoApi = retrofit.create(
        SchoolInfoApi::class.java)

//    @Provides
//    fun provideAreaService(@Named("provideAreaRetrofit") retrofit: Retrofit): AreaService = retrofit.create(
//        AreaService::class.java)

    @Provides
    fun provideUnivService(@Named("provideUnivRetrofit") retrofit: Retrofit): UnivInfoApi = retrofit.create(
        UnivInfoApi::class.java)
}