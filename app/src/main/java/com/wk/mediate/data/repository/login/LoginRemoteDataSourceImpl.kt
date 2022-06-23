package com.wk.mediate.data.repository.login

import com.wk.mediate.data.api.LoginApi
import com.wk.mediate.data.models.Login
import com.wk.mediate.data.models.LoginResult
import retrofit2.Response
import javax.inject.Inject

class LoginRemoteDataSourceImpl @Inject constructor(
    private val loginApi: LoginApi
) : LoginRemoteDataSource {
    override suspend fun getLoginResult(login: Login): Response<LoginResult> {
        return loginApi.getLoginResult(login)
    }
}