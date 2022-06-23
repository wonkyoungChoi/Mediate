package com.wk.mediate.domain.usecase.register

import com.wk.mediate.data.models.BasicInfo
import com.wk.mediate.domain.models.RegisterEntity
import com.wk.mediate.domain.repository.RegisterRepository
import com.wk.mediate.present.utils.DataHandler
import javax.inject.Inject

class GetBasicRegisterResultUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(info: BasicInfo): DataHandler<RegisterEntity> = registerRepository.getBasicRegister(info)
}