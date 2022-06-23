package com.wk.mediate.domain.repository

import com.wk.mediate.domain.models.SearchEntity
import com.wk.mediate.present.utils.DataHandler

interface UnivInfoRepository {
    suspend fun getUnivInfo(search: String): DataHandler<SearchEntity>
}
