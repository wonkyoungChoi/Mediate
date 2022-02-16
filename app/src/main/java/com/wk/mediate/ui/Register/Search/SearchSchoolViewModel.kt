package com.wk.mediate.ui.Register.Search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wk.mediate.repository.SearchSchoolRepository

class SearchSchoolViewModel : ViewModel() {
    private val repository = SearchSchoolRepository()

    private val getSchoolResult: LiveData<ArrayList<String>>
        get() = repository._schoolResult

    fun loadSchool(search: String){
        repository.loadSchoolResult(search)
    }

    fun getSchoolResult(): LiveData<ArrayList<String>> {
        return getSchoolResult
    }

    private val getUniversityResult: LiveData<ArrayList<String>>
        get() = repository._universityResult

    fun loadUniversity(search: String){
        repository.loadUniversityResult(search)
    }

    fun getUniversityResult(): LiveData<ArrayList<String>> {
        return getUniversityResult
    }


}