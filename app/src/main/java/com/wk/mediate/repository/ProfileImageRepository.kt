package com.wk.mediate.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wk.mediate.network.BasicAPI
import com.wk.mediate.ui.Login.LoginResult
import com.wk.mediate.ui.ProfileImageResult
import okhttp3.MultipartBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileImageRepository {

    var _result = MutableLiveData<String>()


    fun loadProfileImage(file: MultipartBody.Part) {

        val call = BasicAPI.createProfileImageApi().loadImageUpload(file)

        call.enqueue(object : Callback<ProfileImageResult> {
            override fun onResponse(call: Call<ProfileImageResult>, response: Response<ProfileImageResult>) {
                Log.d("responseCode", response.code().toString())

                when (response.code()) {
                    201 -> {
                        Log.d("responseUrl", response.body()?.url!!)
                        _result.value = response.body()?.url
                    }
                }

            }

            override fun onFailure(call: Call<ProfileImageResult>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
