package com.wk.mediate.domain.repository

import com.wk.mediate.domain.models.SearchEntity
import com.wk.mediate.present.utils.DataHandler

interface SchoolInfoRepository {
    suspend fun getSchoolInfo(search: String): DataHandler<SearchEntity>
}