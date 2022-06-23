package com.wk.mediate.data.api

import com.wk.mediate.present.views.register.selectInfo.school.search.SearchUnivResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//대학교 정보 가져오기
interface UnivInfoApi {
    @GET("cnet/openapi/getOpenApi/")
    fun loadUnivInfo(@Query("apiKey") key: String, @Query("svcType") name: String, @Query("svcCode") school: String,
                       @Query("contentType") type: String, @Query("gubun") gubun: String, @Query("searchSchulNm") search: String): Response<SearchUnivResult>
}