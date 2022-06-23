package com.wk.mediate.domain.usecase.search

import com.wk.mediate.domain.models.SearchEntity
import com.wk.mediate.domain.repository.SchoolInfoRepository
import com.wk.mediate.present.utils.DataHandler
import javax.inject.Inject

class GetSearchSchoolInfoUseCase @Inject constructor(
    private val schoolInfoRepository: SchoolInfoRepository
) {
    suspend operator fun invoke(search: String): DataHandler<SearchEntity> = schoolInfoRepository.getSchoolInfo(search)
}
