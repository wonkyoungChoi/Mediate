package com.wk.mediate.repository

import androidx.lifecycle.MutableLiveData
import com.wk.mediate.network.BasicAPI
import com.wk.mediate.ui.Register.BasicInfo.BasicInfo
import com.wk.mediate.ui.Register.BasicInfo.RegisterResult
import com.wk.mediate.ui.Register.Select.SelectInfoTutee
import com.wk.mediate.ui.Register.Select.SelectInfoTutor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository {
    var _basicResult = MutableLiveData<String>()
    var _selectResult = MutableLiveData<String>()

    fun loadBasicRegister(info: BasicInfo) {
        val call = BasicAPI.createBasicRegisterApi().loadBasicRegister(info)
        call.enqueue(object : Callback<RegisterResult> {
            override fun onResponse(call: Call<RegisterResult>, response: Response<RegisterResult>) {
                when (response.code()) {
                    400 -> {
                        _basicResult.value = JSONObject(response.errorBody()!!.string()).getString("message")
                    }
                    200 ->{
                        _basicResult.value = response.body()!!.message
                    }
                }

            }

            override fun onFailure(call: Call<RegisterResult>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun loadSelectTutorRegister(info: SelectInfoTutor) {
        val call = BasicAPI.createSelectTutorRegisterApi().loadSelectTutorRegister(info)
        call.enqueue(object : Callback<RegisterResult> {
            override fun onResponse(call: Call<RegisterResult>, response: Response<RegisterResult>) {
                when (response.code()) {
                    400 -> {
                        _selectResult.value = JSONObject(response.errorBody()!!.string()).getString("message")
                    }
                    200 ->{
                        _selectResult.value = response.body()!!.message
                    }
                }

            }

            override fun onFailure(call: Call<RegisterResult>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun loadSelectTuteeRegister(info: SelectInfoTutee) {
        val call = BasicAPI.createSelectTuteeRegisterApi().loadSelectTuteeRegister(info)
        call.enqueue(object : Callback<RegisterResult> {
            override fun onResponse(call: Call<RegisterResult>, response: Response<RegisterResult>) {
                when (response.code()) {
                    400 -> {
                        _selectResult.value = JSONObject(response.errorBody()!!.string()).getString("message")
                    }
                    200 ->{
                        _selectResult.value = response.body()!!.message
                    }
                }

            }

            override fun onFailure(call: Call<RegisterResult>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}