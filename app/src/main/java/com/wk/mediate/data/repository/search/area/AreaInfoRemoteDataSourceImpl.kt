package com.wk.mediate.data.repository.search.area

import com.wk.mediate.data.api.AreaInfoApi
import com.wk.mediate.data.models.SearchAreaResult
import retrofit2.Response
import javax.inject.Inject

class AreaInfoRemoteDataSourceImpl @Inject constructor(
    private val areaInfoApi: AreaInfoApi
) : AreaInfoRemoteDataSource {
    override suspend fun getAreaInfoResult(regcode:String, ignore:String): Response<SearchAreaResult> {
        return areaInfoApi.loadArea(regcode, ignore)
    }
}
