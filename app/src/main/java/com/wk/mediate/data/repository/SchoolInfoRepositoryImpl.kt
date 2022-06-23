package com.wk.mediate.data.repository

import com.wk.mediate.BuildConfig
import com.wk.mediate.data.mapper.SchoolInfoMapper
import com.wk.mediate.data.models.SearchSchoolResult
import com.wk.mediate.data.repository.search.school.SchoolInfoRemoteDataSource
import com.wk.mediate.domain.models.SearchEntity
import com.wk.mediate.domain.repository.SchoolInfoRepository
import com.wk.mediate.present.utils.DataHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SchoolInfoRepositoryImpl @Inject constructor(
    private val schoolInfoRemoteDataSource: SchoolInfoRemoteDataSource
) : SchoolInfoRepository {
    override suspend fun getSchoolInfo(search: String): DataHandler<SearchEntity> {
        return try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                schoolInfoRemoteDataSource.getSchoolInfoResult(BuildConfig.SCHOOL_API_KEY,"json", search)
            }
            handleResponse(response)
        } catch (error: Exception) {
            DataHandler.FAIL()
        }
    }

    private fun handleResponse(response: Response<SearchSchoolResult>): DataHandler<SearchEntity> {
        if(response.isSuccessful && response.body() != null) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(SchoolInfoMapper(it))
            }
        }
        return DataHandler.ERROR(message = response.errorBody()!!.string())
    }

}