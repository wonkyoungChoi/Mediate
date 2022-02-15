package com.wk.mediate.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wk.mediate.BuildConfig
import com.wk.mediate.network.SchoolApi
import com.wk.mediate.ui.Register.Search.SchoolInfo
import com.wk.mediate.ui.Register.Search.SearchSchoolResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchSchoolRepository {
    var _schoolResult = MutableLiveData<ArrayList<String>>()


    fun loadSchoolResult(search: String) {
        val call = SchoolApi.createApi().loadSchool(BuildConfig.SCHOOL_API_KEY,"json", search)
        Log.d("LoadURL", call.request().url().toString())
        //Authorization

        call.enqueue(object : Callback<SearchSchoolResult> {
            override fun onResponse(call: Call<SearchSchoolResult>, response: Response<SearchSchoolResult>) {
                val items = ArrayList<String>()
                val list: SearchSchoolResult? = response.body()
                val num: Int

                if (list != null) {
                    try {
                        num = if (list.schoolInfo?.get(0)?.head?.get(0)?.listTotalCount!! > 20) 5
                        else list.schoolInfo?.get(0)?.head?.get(0)?.listTotalCount!! - 1
                        Log.d("NUM", num.toString())
                        for (i in 0..num) {
                            val item = list.schoolInfo?.get(1)?.row?.get(i)?.schulNm.toString()
                            if(item.contains("고등")) items.add(item)
                        }
                    } catch (e: NullPointerException) {
                        items.add("검색한 학교가 없습니다.")
                        _schoolResult.value = items
                    }
                }
                _schoolResult.value = items
            }

            override fun onFailure(call: Call<SearchSchoolResult>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}