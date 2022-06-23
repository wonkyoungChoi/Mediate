package com.wk.mediate.data.di

import com.wk.mediate.data.api.LoginApi
import com.wk.mediate.data.api.RegisterApi
import com.wk.mediate.data.api.SchoolInfoApi
import com.wk.mediate.data.api.UnivInfoApi
import com.wk.mediate.data.repository.login.LoginRemoteDataSource
import com.wk.mediate.data.repository.login.LoginRemoteDataSourceImpl
import com.wk.mediate.data.repository.register.RegisterRemoteDataSource
import com.wk.mediate.data.repository.register.RegisterRemoteDataSourceImpl
import com.wk.mediate.data.repository.search.school.SchoolInfoRemoteDataSource
import com.wk.mediate.data.repository.search.school.SchoolInfoRemoteDataSourceImpl
import com.wk.mediate.data.repository.search.univ.UnivInfoRemoteDataSource
import com.wk.mediate.data.repository.search.univ.UnivInfoRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {
    @Provides
    @Singleton
    fun provideLoginRemoteDataSource(loginApi: LoginApi): LoginRemoteDataSource {
        return LoginRemoteDataSourceImpl(loginApi)
    }

    @Provides
    @Singleton
    fun provideRegisterRemoteDataSource(registerApi: RegisterApi): RegisterRemoteDataSource {
        return RegisterRemoteDataSourceImpl(registerApi)
    }

    @Provides
    @Singleton
    fun provideSchoolInfoRemoteDataSource(schoolInfoApi: SchoolInfoApi): SchoolInfoRemoteDataSource {
        return SchoolInfoRemoteDataSourceImpl(schoolInfoApi)
    }

    @Provides
    @Singleton
    fun provideUnivInfoRemoteDataSource(univInfoApi: UnivInfoApi): UnivInfoRemoteDataSource {
        return UnivInfoRemoteDataSourceImpl(univInfoApi)
    }

}