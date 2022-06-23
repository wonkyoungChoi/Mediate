package com.wk.mediate.data.repository

import com.wk.mediate.BuildConfig
import com.wk.mediate.data.mapper.UnivInfoMapper
import com.wk.mediate.data.repository.search.univ.UnivInfoRemoteDataSource
import com.wk.mediate.domain.models.SearchEntity
import com.wk.mediate.domain.repository.SchoolInfoRepository
import com.wk.mediate.domain.repository.UnivInfoRepository
import com.wk.mediate.present.utils.DataHandler
import com.wk.mediate.present.views.register.selectInfo.school.search.SearchUnivResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UnivInfoRepositoryImpl @Inject constructor(
    private val univInfoRemoteDataSource: UnivInfoRemoteDataSource
) : UnivInfoRepository {
    override suspend fun getUnivInfo(search: String): DataHandler<SearchEntity> {
        return try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                univInfoRemoteDataSource.getUnivInfoResult(BuildConfig.UNIVERSITY_API_KEY, "api","SCHOOL", "json", "univ_list", search)
            }
            handleResponse(response)
        } catch (error: Exception) {
            DataHandler.FAIL()
        }
    }

    private fun handleResponse(response: Response<SearchUnivResult>): DataHandler<SearchEntity> {
        if(response.isSuccessful && response.body() != null) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(UnivInfoMapper(it))
            }
        }
        return DataHandler.ERROR(message = response.errorBody()!!.string())
    }

}