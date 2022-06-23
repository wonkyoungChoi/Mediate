package com.wk.mediate.domain.usecase.register

import com.wk.mediate.data.models.TutorInfo
import com.wk.mediate.domain.models.RegisterEntity
import com.wk.mediate.domain.repository.RegisterRepository
import com.wk.mediate.present.utils.DataHandler
import javax.inject.Inject

class GetTutorRegisterResultUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(info: TutorInfo): DataHandler<RegisterEntity> = registerRepository.getSelectTutorRegister(info)
}