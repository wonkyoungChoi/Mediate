package com.wk.mediate.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData

class SelectSubjectRepository {
    var _result = MutableLiveData<List<String>>()
    var subjects = arrayOf("초등수학", "중등수학", "고등수학")
    fun loadSubjectResult(search: String) {
        val items = ArrayList<String>()
        items.add("\"$search\" 입력")

        for(i: Int in subjects.indices) {
            if(subjects[i].contains(search)) {
                Log.d("test", subjects[i])
                items.add(subjects[i])
            }
        }
        _result.value = items
    }

}



