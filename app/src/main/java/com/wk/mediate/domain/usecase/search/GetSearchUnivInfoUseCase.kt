package com.wk.mediate.domain.usecase.search

import com.wk.mediate.domain.models.SearchEntity
import com.wk.mediate.domain.repository.UnivInfoRepository
import com.wk.mediate.present.utils.DataHandler
import javax.inject.Inject

class GetSearchUnivInfoUseCase @Inject constructor(
    private val univInfoRepository: UnivInfoRepository
) {
    suspend operator fun invoke(search: String): DataHandler<SearchEntity> = univInfoRepository.getUnivInfo(search)
}