package com.wk.mediate.present.views.register.basicInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wk.mediate.data.models.BasicInfo
import com.wk.mediate.domain.models.RegisterEntity
import com.wk.mediate.domain.usecase.register.GetBasicRegisterResultUseCase
import com.wk.mediate.present.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasicRegisterViewModel @Inject constructor(private val getBasicRegisterResultUseCase: GetBasicRegisterResultUseCase) : ViewModel() {
    private val _registerResult = MutableLiveData<DataHandler<RegisterEntity>>()

    val registerResult: LiveData<DataHandler<RegisterEntity>> = _registerResult

    fun getLivedataBasicRegisterResult(info: BasicInfo) {
        _registerResult.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = getBasicRegisterResultUseCase.invoke(info)
            _registerResult.postValue(response)
        }
    }
}