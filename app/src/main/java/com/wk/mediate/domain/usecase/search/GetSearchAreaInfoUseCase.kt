package com.wk.mediate.domain.usecase.search

import com.wk.mediate.domain.models.SearchAreaEntity
import com.wk.mediate.domain.models.SearchEntity
import com.wk.mediate.domain.repository.AreaInfoRepository
import com.wk.mediate.domain.repository.SchoolInfoRepository
import com.wk.mediate.present.utils.DataHandler
import javax.inject.Inject

class GetSearchAreaInfoUseCase @Inject constructor(
    private val areaInfoRepository: AreaInfoRepository
) {
    suspend operator fun invoke(search: String): DataHandler<SearchAreaEntity> = areaInfoRepository.getAreaInfo(search)
}