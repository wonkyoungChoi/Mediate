package com.wk.mediate.network

import android.util.Log
import com.wk.mediate.common.Login
import com.wk.mediate.common.LoginResult
import com.wk.mediate.common.Pref
import com.wk.mediate.ui.ProfileImageResult
import com.wk.mediate.ui.Register.BasicInfo.BasicInfo
import com.wk.mediate.ui.Register.BasicInfo.RegisterResult
import com.wk.mediate.ui.Register.SelectInfo.SelectInfoTutee
import com.wk.mediate.ui.Register.SelectInfo.SelectInfoTutor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


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
    @POST("api/signin")
    suspend fun loadLogin(@Body loginData: Login): Call<LoginResult>
}

interface ImageUploadService {
    @Multipart
    @POST("api/profile-image")
    fun loadImageUpload(@Part file: MultipartBody.Part): Call<ProfileImageResult>
}

object BasicAPI {
    private const val baseUrl = "http://3.37.10.226/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val oAuthRetrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder().addHeader("Authorization", "${Pref.auth?.accessToken}").build()
            chain.proceed(request)
        }.build())
        .build()

    //기본정보 회원가입
    fun createBasicRegisterApi(): BasicRegisterService {
        return retrofit.create(
            BasicRegisterService::class.java
        )
    }

    //튜터 회원가입
    fun createSelectTutorRegisterApi(): SelectTutorRegisterService {
        return retrofit.create(
            SelectTutorRegisterService::class.java
        )
    }

    //튜티 회원가입
    fun createSelectTuteeRegisterApi(): SelectTuteeRegisterService {
        return retrofit.create(
            SelectTuteeRegisterService::class.java
        )
    }

    //로그인
    fun createLoginApi(): LoginService {
        return retrofit.create(
            LoginService::class.java
        )
    }

    //프로필 이미지 업로드
    fun createProfileImageApi(): ImageUploadService {
        Log.d("PrefAccess", Pref.auth?.accessToken.toString())
        return oAuthRetrofit.create(
            ImageUploadService::class.java
        )
    }
}
