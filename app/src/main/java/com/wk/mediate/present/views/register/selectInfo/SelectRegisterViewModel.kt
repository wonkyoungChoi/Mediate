package com.wk.mediate.present.views.register.selectInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wk.mediate.data.models.TuteeInfo
import com.wk.mediate.data.models.TutorInfo
import com.wk.mediate.domain.models.RegisterEntity
import com.wk.mediate.domain.usecase.register.GetTuteeRegisterResultUseCase
import com.wk.mediate.domain.usecase.register.GetTutorRegisterResultUseCase
import com.wk.mediate.present.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectRegisterViewModel @Inject constructor(private val getTutorRegisterResultUseCase: GetTutorRegisterResultUseCase,
                                                  private val getTuteeRegisterResultUseCase: GetTuteeRegisterResultUseCase) : ViewModel() {
    private val _registerResult = MutableLiveData<DataHandler<RegisterEntity>>()

    val registerResult: LiveData<DataHandler<RegisterEntity>> = _registerResult

    fun getLivedataSelectTutorRegisterResult(info: TutorInfo) {
        _registerResult.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = getTutorRegisterResultUseCase.invoke(info)
            _registerResult.postValue(response)
        }
    }

    fun getLivedataSelectTuteeRegisterResult(info: TuteeInfo) {
        _registerResult.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = getTuteeRegisterResultUseCase.invoke(info)
            _registerResult.postValue(response)
        }
    }
}
