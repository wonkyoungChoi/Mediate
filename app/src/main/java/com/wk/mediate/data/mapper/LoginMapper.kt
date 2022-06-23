package com.wk.mediate.data.mapper

import com.wk.mediate.domain.models.LoginEntity
import com.wk.mediate.data.models.LoginResult

object LoginMapper {
    operator fun invoke(loginResult: LoginResult): LoginEntity {
        return LoginEntity(
            loginResult.accessToken,
            loginResult.refreshToken
        )
    }
}