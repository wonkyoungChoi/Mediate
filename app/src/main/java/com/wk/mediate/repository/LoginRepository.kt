package com.wk.mediate.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wk.mediate.common.Login
import com.wk.mediate.common.LoginResult
import com.wk.mediate.network.BasicAPI
import com.wk.mediate.network.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class LoginRepository @Inject constructor(private val service : LoginService) {
    suspend fun loadLogin(loginData: Login) = service.loadLogin(loginData)
//    var _result = MutableLiveData<LoginResult>()

//    fun loadLogin(loginData: Login) {
//        Log.d("Load", loginData.toString())
//
//        val call = BasicAPI.createLoginApi().loadLogin(loginData)
//
//        call.enqueue(object : Callback<LoginResult> {
//            override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
//                Log.d("responseCode", response.code().toString())
//                Log.d("responseBody", response.message())
//                when (response.code()) {
//                    400 -> {
////                        val jsonArray: JSONArray = JSONObject(response.errorBody()!!.string()).getJSONArray("message")
////                        val message: String = jsonArray.getString(0)
//                        _result.value = LoginResult("", "")
//                    }
//                    200 -> {
//                        _result.value = response.body()
//                    }
//                }
//
//            }
//
//            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
//                 t.printStackTrace()
//            }
//        })
//    }
}