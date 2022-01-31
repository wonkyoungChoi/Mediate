package com.wk.mediate.ui.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wk.mediate.repository.LoginRepository

class LoginViewModel : ViewModel() {
    private val loginRepository = LoginRepository()
    private val result: LiveData<LoginResult>
        get() = loginRepository._result

    fun loadLogin(login: Login){
        loginRepository.loadLogin(login)
    }

    fun getLoginResult(): LiveData<LoginResult> {
        return result
    }

}
