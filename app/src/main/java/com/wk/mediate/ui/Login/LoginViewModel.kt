package com.wk.mediate.ui.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wk.mediate.common.Login
import com.wk.mediate.common.LoginResult
import com.wk.mediate.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
    private val result: LiveData<LoginResult>
        get() = repository._result

    fun loadLogin(login: Login){
        repository.loadLogin(login)
    }

    fun getLoginResult(): LiveData<LoginResult> {
        return result
    }

}
