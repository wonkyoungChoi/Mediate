package com.wk.mediate.data.repository.search.univ

import com.wk.mediate.data.api.UnivInfoApi
import com.wk.mediate.present.views.register.selectInfo.school.search.SearchUnivResult
import retrofit2.Response
import javax.inject.Inject

class UnivInfoRemoteDataSourceImpl @Inject constructor(
    private val univInfoApi: UnivInfoApi
) : UnivInfoRemoteDataSource {
    override suspend fun getUnivInfoResult(
        key: String,
        name: String,
        school: String,
        type: String,
        gubun: String,
        search: String
    ): Response<SearchUnivResult> {
        return univInfoApi.loadUnivInfo(key, name, school, type, gubun, search)
    }

}
