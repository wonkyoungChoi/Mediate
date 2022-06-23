package com.wk.mediate.data.repository.register

import com.wk.mediate.data.models.BasicInfo
import com.wk.mediate.data.models.RegisterResult
import com.wk.mediate.data.models.TuteeInfo
import com.wk.mediate.data.models.TutorInfo
import retrofit2.Response


interface RegisterRemoteDataSource {
    suspend fun getBasicRegisterResult(info: BasicInfo): Response<RegisterResult>
    suspend fun getSelectTutorRegisterResult(info: TutorInfo): Response<RegisterResult>
    suspend fun getSelectTuteeRegisterResult(info: TuteeInfo): Response<RegisterResult>
}