package com.wk.mediate.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wk.mediate.network.BasicAPI
import com.wk.mediate.ui.Login.Login
import com.wk.mediate.ui.Login.LoginResult
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginRepository {

    var _result = MutableLiveData<LoginResult>()


    fun loadLogin(loginData: Login) {
        Log.d("Load", loginData.toString())

        val call = BasicAPI.createLoginApi().loadLogin(loginData)

        call.enqueue(object : Callback<LoginResult> {
            override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                Log.d("responseCode", response.code().toString())
                when (response.code()) {
                    400 -> {
                        val jsonArray: JSONArray = JSONObject(response.errorBody()!!.string()).getJSONArray("message")
                        val message: String = jsonArray.getString(0)
                        Log.d("Success", jsonArray.getString(0))
                        _result.value = LoginResult(message)
                    }
                    200 -> _result.value = LoginResult(response.body()?.token)
                }

            }

            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                 t.printStackTrace()
            }
        })
    }
}