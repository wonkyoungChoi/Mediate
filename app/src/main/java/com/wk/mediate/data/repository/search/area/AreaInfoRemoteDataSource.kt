package com.wk.mediate.data.repository.search.area

import com.wk.mediate.data.models.SearchAreaResult
import retrofit2.Response

interface AreaInfoRemoteDataSource {
    suspend fun getAreaInfoResult(regcode:String, ignore:String): Response<SearchAreaResult>
}