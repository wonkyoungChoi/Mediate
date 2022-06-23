package com.wk.mediate.domain.repository

import com.wk.mediate.domain.models.SearchAreaEntity
import com.wk.mediate.present.utils.DataHandler

interface AreaInfoRepository {
    suspend fun getAreaInfo(search: String): DataHandler<SearchAreaEntity>
}