package com.wk.mediate.data.repository

import com.wk.mediate.data.mapper.RegisterMapper
import com.wk.mediate.data.models.BasicInfo
import com.wk.mediate.data.models.RegisterResult
import com.wk.mediate.data.models.TuteeInfo
import com.wk.mediate.data.models.TutorInfo
import com.wk.mediate.data.repository.register.RegisterRemoteDataSource
import com.wk.mediate.domain.models.RegisterEntity
import com.wk.mediate.domain.repository.RegisterRepository
import com.wk.mediate.present.utils.DataHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerRemoteDataSource: RegisterRemoteDataSource
) : RegisterRepository {
    override suspend fun getBasicRegister(info: BasicInfo): DataHandler<RegisterEntity> {
        return try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                registerRemoteDataSource.getBasicRegisterResult(info)
            }
            handleResponse(response)
        } catch (error: Exception) {
            DataHandler.FAIL()
        }
    }

    override suspend fun getSelectTutorRegister(info: TutorInfo): DataHandler<RegisterEntity> {
        return try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                registerRemoteDataSource.getSelectTutorRegisterResult(info)
            }
            handleResponse(response)
        } catch (error: Exception) {
            DataHandler.FAIL()
        }
    }

    override suspend fun getSelectTuteeRegister(info: TuteeInfo): DataHandler<RegisterEntity> {
        return try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                registerRemoteDataSource.getSelectTuteeRegisterResult(info)
            }
            handleResponse(response)
        } catch (error: Exception) {
            DataHandler.FAIL()
        }
    }

    private fun handleResponse(response: Response<RegisterResult>): DataHandler<RegisterEntity> {
        if(response.isSuccessful && response.body() != null) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(RegisterMapper(it))
            }
        }
        return DataHandler.ERROR(message = response.errorBody()!!.string())
    }



}