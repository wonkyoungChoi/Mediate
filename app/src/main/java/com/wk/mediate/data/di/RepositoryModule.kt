package com.wk.mediate.data.di

import com.wk.mediate.data.repository.LoginRepositoryImpl
import com.wk.mediate.data.repository.RegisterRepositoryImpl
import com.wk.mediate.data.repository.SchoolInfoRepositoryImpl
import com.wk.mediate.data.repository.UnivInfoRepositoryImpl
import com.wk.mediate.data.repository.login.LoginRemoteDataSource
import com.wk.mediate.data.repository.register.RegisterRemoteDataSource
import com.wk.mediate.data.repository.search.school.SchoolInfoRemoteDataSource
import com.wk.mediate.data.repository.search.univ.UnivInfoRemoteDataSource
import com.wk.mediate.domain.repository.LoginRepository
import com.wk.mediate.domain.repository.RegisterRepository
import com.wk.mediate.domain.repository.SchoolInfoRepository
import com.wk.mediate.domain.repository.UnivInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideLoginRepository(loginRemoteDataSource: LoginRemoteDataSource): LoginRepository {
        return LoginRepositoryImpl(loginRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(registerRemoteDataSource: RegisterRemoteDataSource): RegisterRepository {
        return RegisterRepositoryImpl(registerRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideSchoolInfoRepository(schoolInfoRemoteDataSource: SchoolInfoRemoteDataSource): SchoolInfoRepository {
        return SchoolInfoRepositoryImpl(schoolInfoRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideUnivInfoRepository(univInfoRemoteDataSource: UnivInfoRemoteDataSource): UnivInfoRepository {
        return UnivInfoRepositoryImpl(univInfoRemoteDataSource)
    }
}