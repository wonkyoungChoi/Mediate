package com.wk.mediate.domain.usecase.register

import com.wk.mediate.data.models.TuteeInfo
import com.wk.mediate.domain.models.RegisterEntity
import com.wk.mediate.domain.repository.RegisterRepository
import com.wk.mediate.present.utils.DataHandler
import javax.inject.Inject

class GetTuteeRegisterResultUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(info: TuteeInfo): DataHandler<RegisterEntity> = registerRepository.getSelectTuteeRegister(info)
}