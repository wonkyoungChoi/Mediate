package com.wk.mediate.ui.Login

data class Login (
        var id: String?,
        var password: String?
)

data class LoginResult (
        var token: String?
)