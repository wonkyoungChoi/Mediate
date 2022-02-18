package com.wk.mediate.ui.Register.SelectInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wk.mediate.repository.SearchSchoolRepository

class SelectInfoViewModel : ViewModel() {
    private val repository = SearchSchoolRepository()

    private val getSchoolResult: LiveData<List<String>>
        get() = repository._schoolResult

    fun loadSchool(search: String){
        repository.loadSchoolResult(search)
    }

    fun getSchoolResult(): LiveData<List<String>> {
        return getSchoolResult
    }

    private val getUniversityResult: LiveData<List<String>>
        get() = repository._universityResult

    fun loadUniversity(search: String){
        repository.loadUniversityResult(search)
    }

    fun getUniversityResult(): LiveData<List<String>> {
        return getUniversityResult
    }

    private val getMajorResult: LiveData<List<String>>
        get() = repository._majorResult

    fun loadMajor(search: String) {
        repository.loadMajorResult(search)
    }

    fun getMajorResult(): LiveData<List<String>> {
        return getMajorResult
    }


}