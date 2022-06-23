package com.wk.mediate.present.views.register.selectInfo.school

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wk.mediate.domain.models.SchoolInfoEntity
import com.wk.mediate.domain.models.SearchEntity
import com.wk.mediate.domain.models.UnivInfoEntity
import com.wk.mediate.present.utils.DataHandler
import com.wk.mediate.domain.usecase.search.GetSearchSchoolInfoUseCase
import com.wk.mediate.domain.usecase.search.GetSearchUnivInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchInfoViewModel @Inject constructor(private val schoolInfoUseCase: GetSearchSchoolInfoUseCase, private val univInfoUseCase: GetSearchUnivInfoUseCase): ViewModel() {
    private val _searchResult = MutableLiveData<DataHandler<SearchEntity>>()


    val searchResult: LiveData<DataHandler<SearchEntity>> = _searchResult

    fun getLivedataSchoolResult(search: String) {
        _searchResult.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = schoolInfoUseCase.invoke(search)
            _searchResult.postValue(response)
        }
    }

    fun getLivedataUnivResult(search: String) {
        _searchResult.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = univInfoUseCase.invoke(search)
            _searchResult.postValue(response)
        }
    }
}