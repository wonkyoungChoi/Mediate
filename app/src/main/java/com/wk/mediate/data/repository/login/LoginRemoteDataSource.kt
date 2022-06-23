package com.wk.mediate.data.repository.login

import com.wk.mediate.data.models.Login
import com.wk.mediate.data.models.LoginResult
import retrofit2.Response

interface LoginRemoteDataSource {
    suspend fun getLoginResult(login: Login): Response<LoginResult>
}