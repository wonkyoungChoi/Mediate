package com.wk.mediate.data.repository

import com.wk.mediate.data.mapper.AreaInfoMapper
import com.wk.mediate.data.models.SearchAreaResult
import com.wk.mediate.data.repository.search.area.AreaInfoRemoteDataSource
import com.wk.mediate.domain.models.SearchAreaEntity
import com.wk.mediate.domain.repository.AreaInfoRepository
import com.wk.mediate.present.utils.DataHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class AreaInfoRepositoryImpl @Inject constructor(
    private val areaInfoRemoteDataSource: AreaInfoRemoteDataSource
) : AreaInfoRepository {
    override suspend fun getAreaInfo(search: String): DataHandler<SearchAreaEntity> {
        return try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                areaInfoRemoteDataSource.getAreaInfoResult(search, "false")
            }
            handleResponse(response)
        } catch (error: Exception) {
            DataHandler.FAIL()
        }
    }

    private fun handleResponse(response: Response<SearchAreaResult>): DataHandler<SearchAreaEntity> {
        if(response.isSuccessful && response.body() != null) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(AreaInfoMapper(it))
            }
        }
        return DataHandler.ERROR(message = response.errorBody()!!.string())
    }

}
