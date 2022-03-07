package com.wk.mediate.ui.Register.Select.Subject

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wk.mediate.repository.SelectSubjectRepository

class SelectSubjectViewModel : ViewModel() {
    private val repository = SelectSubjectRepository()

    private val getSchoolResult: LiveData<List<String>>
        get() = repository._result

    fun loadSubject(search: String){
        repository.loadSubjectResult(search)
    }

    fun getSubjectResult(): LiveData<List<String>> {
        return getSchoolResult
    }
}