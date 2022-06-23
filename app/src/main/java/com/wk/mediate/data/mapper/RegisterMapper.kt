package com.wk.mediate.data.mapper

import com.wk.mediate.data.models.RegisterResult
import com.wk.mediate.domain.models.RegisterEntity

object RegisterMapper {
    operator fun invoke(registerResult: RegisterResult): RegisterEntity {
        return RegisterEntity(
            registerResult.body,
            registerResult.message
        )
    }
}