package com.wk.mediate.ui.Register.Search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wk.mediate.repository.LoginRepository
import com.wk.mediate.repository.SearchSchoolRepository
import com.wk.mediate.ui.Login.Login
import com.wk.mediate.ui.Login.LoginResult

class SearchSchoolViewModel : ViewModel() {
    private val repository = SearchSchoolRepository()
    private val getSchoolResult: LiveData<ArrayList<String>>
        get() = repository._schoolResult

    fun loadSchool(name: String){
        repository.loadSchoolResult(name)
    }

    fun getSchoolResult(): LiveData<ArrayList<String>> {
        return getSchoolResult
    }

}