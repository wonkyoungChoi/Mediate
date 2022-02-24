package com.wk.mediate.network

import com.wk.mediate.ui.Register.Select.School.Search.SearchMajorResult
import com.wk.mediate.ui.Register.Select.School.Search.SearchUniversityResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UniversityInfoService {
    @GET("cnet/openapi/getOpenApi/")
    fun loadUniversity(@Query("apiKey") key: String, @Query("svcType") name: String, @Query("svcCode") school: String,
                  @Query("contentType") type: String, @Query("gubun") gubun: String, @Query("searchSchulNm") search: String): Call<SearchUniversityResult>
}

interface MajorInfoService {
    @GET("cnet/openapi/getOpenApi/")
    fun loadMajor(@Query("apiKey") key: String, @Query("svcType") name: String, @Query("svcCode") school: String,
                       @Query("contentType") type: String, @Query("gubun") gubun: String, @Query("searchTitle") search: String): Call<SearchMajorResult>
}

object UniversityApi {
    private const val baseUrl = "https://www.career.go.kr/"
    private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun createUnivApi(): UniversityInfoService {
        return retrofit.create(
                UniversityInfoService::class.java)
    }

    fun createMajorApi(): MajorInfoService {
        return retrofit.create(
                MajorInfoService::class.java
        )
    }
}