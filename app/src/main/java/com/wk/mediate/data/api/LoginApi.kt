package com.wk.mediate.data.api

import com.wk.mediate.data.models.Login
import com.wk.mediate.data.models.LoginResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {
    @Headers("Content-Type: application/json")
    @POST("api/signin")
    suspend fun getLoginResult(@Body loginData: Login): Response<LoginResult>
}