package com.wk.mediate.network

import com.wk.mediate.ui.Login.Login
import com.wk.mediate.ui.Login.LoginResult
import com.wk.mediate.ui.Register.BasicInfo.BasicInfo
import com.wk.mediate.ui.Register.BasicInfo.RegisterResult
import com.wk.mediate.ui.Register.Select.SelectInfoTutee
import com.wk.mediate.ui.Register.Select.SelectInfoTutor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface BasicRegisterService {
    @Headers("Content-Type: application/json")
    @POST("api/signup")
    fun loadBasicRegister(@Body info: BasicInfo): Call<RegisterResult>
}

interface SelectTutorRegisterService {
    @Headers("Content-Type: application/json")
    @POST("api/tutors/signup")
    fun loadSelectTutorRegister(@Body info: SelectInfoTutor): Call<RegisterResult>
}

interface SelectTuteeRegisterService {
    @Headers("Content-Type: application/json")
    @POST("api/tutees/signup")
    fun loadSelectTuteeRegister(@Body info: SelectInfoTutee): Call<RegisterResult>
}

interface LoginService {
    @Headers("Content-Type: application/json")
    @POST("api/auth")
    fun loadLogin(@Body loginData: Login): Call<LoginResult>
}

object BasicAPI {
    private const val baseUrl = "http://3.37.10.226/"
    private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun createBasicRegisterApi(): BasicRegisterService {
        return retrofit.create(
            BasicRegisterService::class.java)
    }

    fun createSelectTutorRegisterApi(): SelectTutorRegisterService {
        return retrofit.create(
                SelectTutorRegisterService::class.java)
    }

    fun createSelectTuteeRegisterApi(): SelectTuteeRegisterService {
        return retrofit.create(
                SelectTuteeRegisterService::class.java)
    }

    fun createLoginApi(): LoginService {
        return retrofit.create(
            LoginService::class.java)
    }
}
