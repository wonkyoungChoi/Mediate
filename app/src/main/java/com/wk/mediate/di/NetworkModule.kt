package com.wk.mediate.di

import com.wk.mediate.network.BasicAPI
import com.wk.mediate.network.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideLoginService(): LoginService {
        return BasicAPI.createLoginApi()
    }
}