package com.wk.mediate.network

import com.wk.mediate.ui.Register.SelectInfo.School.SearchSchoolResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query

interface SchoolInfoService {
    @POST("hub/schoolInfo")
    fun loadSchool(@Query("Key") key:String, @Query("Type") type: String, @Query("SCHUL_NM") name: String): Call<SearchSchoolResult>
}

object SchoolApi {
    private const val baseUrl = "https://open.neis.go.kr/"
    private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun createApi(): SchoolInfoService {
        return retrofit.create(
                SchoolInfoService::class.java)
    }
}