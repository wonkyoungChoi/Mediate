package com.wk.mediate.present.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wk.mediate.domain.repository.ProfileImageRepository
import okhttp3.MultipartBody

class ProfileImageViewModel : ViewModel() {
    private val profileImageRepository = ProfileImageRepository()
    private val result: LiveData<String>
        get() = profileImageRepository._result

    fun loadImage(file: MultipartBody.Part){
//        profileImageRepository.loadProfileImage(file)
    }

    fun getProfileResult(): LiveData<String> {
        return result
    }

}