package com.wk.mediate.domain.usecase.login

import com.wk.mediate.data.models.Login
import com.wk.mediate.domain.models.LoginEntity
import com.wk.mediate.domain.repository.LoginRepository
import com.wk.mediate.present.utils.DataHandler
import javax.inject.Inject

class GetLoginResultUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(login: Login): DataHandler<LoginEntity> = loginRepository.getLoginResult(login)
}