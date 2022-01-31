package com.wk.mediate.network

import com.wk.mediate.ui.Login.Login
import com.wk.mediate.ui.Login.LoginResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @Headers("Content-Type: application/json")
    @POST("api/auth")
    fun loadLogin(@Body loginData: Login): Call<LoginResult>
}

object LoginApi {
    private const val baseUrl = "http://3.37.10.226/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun createApi(): LoginService {
        return retrofit.create(
            LoginService::class.java)
    }
}