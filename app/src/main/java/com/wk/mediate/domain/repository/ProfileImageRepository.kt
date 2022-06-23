package com.wk.mediate.domain.repository

import androidx.lifecycle.MutableLiveData

class ProfileImageRepository {

    var _result = MutableLiveData<String>()


//    fun loadProfileImage(file: MultipartBody.Part) {
//
//        val call = BasicAPI.createProfileImageApi().loadImageUpload(file)
//
//        call.enqueue(object : Callback<ProfileImageResult> {
//            override fun onResponse(call: Call<ProfileImageResult>, response: Response<ProfileImageResult>) {
//                Log.d("responseCode", response.code().toString())
//
//                when (response.code()) {
//                    201 -> {
//                        Log.d("responseUrl", response.body()?.url!!)
//                        _result.value = response.body()?.url
//                    }
//                }
//
//            }
//
//            override fun onFailure(call: Call<ProfileImageResult>, t: Throwable) {
//                t.printStackTrace()
//            }
//        })
//    }
}
