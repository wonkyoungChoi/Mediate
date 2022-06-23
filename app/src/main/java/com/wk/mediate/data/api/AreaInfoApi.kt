package com.wk.mediate.data.api

import com.wk.mediate.data.models.SearchAreaResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//지역 정보 가져오기
interface AreaInfoApi {
    @GET("v1/regcodes")
    fun loadArea(@Query("regcode_pattern") regcode:String, @Query("is_ignore_zero") ignore:String): Response<SearchAreaResult>
}