package com.wk.mediate.data.repository

import com.wk.mediate.data.mapper.LoginMapper
import com.wk.mediate.data.models.Login
import com.wk.mediate.domain.models.LoginEntity
import com.wk.mediate.data.repository.login.LoginRemoteDataSource
import com.wk.mediate.data.models.LoginResult
import com.wk.mediate.domain.repository.LoginRepository
import com.wk.mediate.present.utils.DataHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource
) : LoginRepository {
    override suspend fun getLoginResult(loginData: Login): DataHandler<LoginEntity> {
        return try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                loginRemoteDataSource.getLoginResult(loginData)
            }
            handleResponse(response)
        } catch (error: Exception) {
            DataHandler.FAIL()
        }
    }

    private fun handleResponse(response: Response<LoginResult>): DataHandler<LoginEntity> {
        if(response.isSuccessful && response.body() != null) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(LoginMapper(it))
            }
        }
        return DataHandler.ERROR(message = response.errorBody()!!.string())
    }

}