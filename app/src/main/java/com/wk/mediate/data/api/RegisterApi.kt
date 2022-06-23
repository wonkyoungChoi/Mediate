package com.wk.mediate.data.api

import com.wk.mediate.data.models.BasicInfo
import com.wk.mediate.data.models.RegisterResult
import com.wk.mediate.data.models.TuteeInfo
import com.wk.mediate.data.models.TutorInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RegisterApi {
    @Headers("Content-Type: application/json")
    @POST("api/signup")
    suspend fun loadBasicRegister(@Body info: BasicInfo): Response<RegisterResult>

    @Headers("Content-Type: application/json")
    @POST("api/tutors/signup")
    suspend fun loadSelectTutorRegister(@Body info: TutorInfo): Response<RegisterResult>

    @Headers("Content-Type: application/json")
    @POST("api/tutees/signup")
    suspend fun loadSelectTuteeRegister(@Body info: TuteeInfo): Response<RegisterResult>
}