package com.wk.mediate.present.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wk.mediate.data.models.Login
import com.wk.mediate.domain.models.LoginEntity
import com.wk.mediate.present.utils.DataHandler
import com.wk.mediate.domain.usecase.login.GetLoginResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val getLoginResultUseCase: GetLoginResultUseCase) : ViewModel() {
    private val _loginResult = MutableLiveData<DataHandler<LoginEntity>>()
    val loginResult: LiveData<DataHandler<LoginEntity>> = _loginResult

    fun getLivedataLoginResult(loginData: Login) {
        _loginResult.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = getLoginResultUseCase.invoke(loginData)
            _loginResult.postValue(response)
        }
    }
}
