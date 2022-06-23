package com.wk.mediate.domain.repository

import com.wk.mediate.data.models.Login
import com.wk.mediate.domain.models.LoginEntity
import com.wk.mediate.present.utils.DataHandler

interface LoginRepository {
    suspend fun getLoginResult(loginData: Login): DataHandler<LoginEntity>
}

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