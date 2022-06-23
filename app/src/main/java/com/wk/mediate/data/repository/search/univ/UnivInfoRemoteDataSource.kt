package com.wk.mediate.data.repository.search.univ

import com.wk.mediate.present.views.register.selectInfo.school.search.SearchUnivResult
import retrofit2.Response

interface UnivInfoRemoteDataSource {
    suspend fun getUnivInfoResult(key: String, name: String, school: String,
                                 type: String, gubun: String, search: String): Response<SearchUnivResult>
}