package com.wk.mediate.data.repository.search.school

import com.wk.mediate.data.api.SchoolInfoApi
import com.wk.mediate.data.models.SearchSchoolResult
import retrofit2.Response
import javax.inject.Inject

class SchoolInfoRemoteDataSourceImpl @Inject constructor(
    private val schoolInfoApi: SchoolInfoApi
) : SchoolInfoRemoteDataSource {
    override suspend fun getSchoolInfoResult(key: String, type: String, name: String): Response<SearchSchoolResult> {
        return schoolInfoApi.loadSchool(key, type, name)
    }
}