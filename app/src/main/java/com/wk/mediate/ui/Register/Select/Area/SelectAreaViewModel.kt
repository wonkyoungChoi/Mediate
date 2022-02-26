package com.wk.mediate.ui.Register.Select.Area

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wk.mediate.repository.SelectAreaRepository

class SelectAreaViewModel : ViewModel() {
    private val repository = SelectAreaRepository()

    private val getDoResult: LiveData<List<String>>
        get() = repository._doResult

    fun loadDo(search: String){
        repository.loadDoResult(search)
    }

    fun getDoResult(): LiveData<List<String>> {
        return getDoResult
    }

    private val getSiGunGuResult: LiveData<List<AreaItem>>
        get() = repository._siGunGuResult

    fun loadSiGunGu(search: String){
        repository.loadSiGunGuResult(search)
    }

    fun getSiGunGuResult(): LiveData<List<AreaItem>> {
        return getSiGunGuResult
    }

    private val getDongResult: LiveData<List<String>>
        get() = repository._dongResult

    fun loadDong(search: String) {
        repository.loadDongResult(search)
    }

    fun getDongResult(): LiveData<List<String>> {
        return getDongResult
    }


}