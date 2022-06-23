package com.wk.mediate.data.repository.search.school

import com.wk.mediate.data.models.*
import retrofit2.Response

interface SchoolInfoRemoteDataSource {
    suspend fun getSchoolInfoResult(key: String, type: String, name: String): Response<SearchSchoolResult>
}