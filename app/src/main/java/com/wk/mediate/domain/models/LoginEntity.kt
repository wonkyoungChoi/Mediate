package com.wk.mediate.domain.models

data class LoginEntity(
    var accessToken: String?,
    var refreshToken: String?
)

