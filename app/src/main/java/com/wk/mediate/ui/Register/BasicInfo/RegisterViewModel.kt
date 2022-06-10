package com.wk.mediate.ui.Register.BasicInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wk.mediate.repository.RegisterRepository
import com.wk.mediate.ui.Register.SelectInfo.SelectInfoTutee
import com.wk.mediate.ui.Register.SelectInfo.SelectInfoTutor

class RegisterViewModel : ViewModel() {
    private val registerRepository = RegisterRepository()
    private val basicResult: LiveData<String>
        get() = registerRepository._basicResult

    private val selectResult: LiveData<String>
        get() = registerRepository._selectResult

    fun loadBasicRegister(info: BasicInfo){
        registerRepository.loadBasicRegister(info)
    }

    fun getBasicRegisterResult(): LiveData<String> {
        return basicResult
    }

    fun loadSelectTutorRegister(info: SelectInfoTutor){
        registerRepository.loadSelectTutorRegister(info)
    }

    fun loadSelectTuteeRegister(info: SelectInfoTutee){
        registerRepository.loadSelectTuteeRegister(info)
    }

    fun getSelectRegisterResult(): LiveData<String> {
        return selectResult
    }
}
