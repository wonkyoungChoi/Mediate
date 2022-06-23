package com.wk.mediate.present.views.register.selectInfo.subject

import androidx.lifecycle.ViewModel
import com.wk.mediate.domain.repository.SelectSubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectSubjectViewModel @Inject constructor(private val repository: SelectSubjectRepository): ViewModel() {
    private val getElementArray: Array<String>
        get() = repository.elementSubjects

    private val getMiddleArray: Array<String>
        get() = repository.middleSubjects

    private val getHighArray: Array<String>
        get() = repository.highSubjects

    private val getEntranceArray: Array<String>
        get() = repository.entranceExam

    private val getComputerArray: Array<String>
        get() = repository.computer

    private val getForeignArray: Array<String>
        get() = repository.foreignLanguage

    private val getInstrumentArray: Array<String>
        get() = repository.instrument


    fun getElementArray(): Array<String> {
        return getElementArray
    }

    fun getMiddleArray(): Array<String> {
        return getMiddleArray
    }

    fun getHighArray(): Array<String> {
        return getHighArray
    }

    fun getEntranceArray(): Array<String> {
        return getEntranceArray
    }

    fun getComputerArray(): Array<String> {
        return getComputerArray
    }

    fun getForeignArray(): Array<String> {
        return getForeignArray
    }

    fun getInstrumentArray(): Array<String> {
        return getInstrumentArray
    }
}