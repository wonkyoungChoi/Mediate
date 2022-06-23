package com.wk.mediate.data.repository.register

import com.wk.mediate.data.api.RegisterApi
import com.wk.mediate.data.models.*
import retrofit2.Response
import javax.inject.Inject

class RegisterRemoteDataSourceImpl @Inject constructor(
    private val registerApi: RegisterApi
) : RegisterRemoteDataSource {
    override suspend fun getBasicRegisterResult(info: BasicInfo): Response<RegisterResult> {
        return registerApi.loadBasicRegister(info)
    }

    override suspend fun getSelectTutorRegisterResult(info: TutorInfo): Response<RegisterResult> {
        return registerApi.loadSelectTutorRegister(info)
    }

    override suspend fun getSelectTuteeRegisterResult(info: TuteeInfo): Response<RegisterResult> {
        return registerApi.loadSelectTuteeRegister(info)
    }
}
