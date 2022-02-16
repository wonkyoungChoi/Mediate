package com.wk.mediate.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wk.mediate.BuildConfig
import com.wk.mediate.network.SchoolApi
import com.wk.mediate.network.UniversityApi
import com.wk.mediate.ui.Register.Search.SearchSchoolResult
import com.wk.mediate.ui.Register.Search.SearchUniversityResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchSchoolRepository {
    var _schoolResult = MutableLiveData<ArrayList<String>>()
    var _universityResult = MutableLiveData<ArrayList<String>>()


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

    fun loadUniversityResult(search: String) {
        val call = UniversityApi.createApi().loadUniversity(BuildConfig.UNIVERSITY_API_KEY, "api","SCHOOL", "json", "univ_list", search)

        call.enqueue(object : Callback<SearchUniversityResult> {
            override fun onResponse(call: Call<SearchUniversityResult>, response: Response<SearchUniversityResult>) {
                Log.d("url", response.raw().toString())
                val items = ArrayList<String>()
                val list: SearchUniversityResult? = response.body()
                val num: Int
                if (list != null) {
                    try {
                        num = if (list.dataSearch.content[0].totalCount > 20) 5
                        else list.dataSearch.content[0].totalCount - 1
                        Log.d("NUM", num.toString())
                        for (i in 0..num) {
                            Log.d("INFO", list.dataSearch.content[i].schoolName)
                            items.add(list.dataSearch.content[i].schoolName)
                        }
                    } catch (e: NullPointerException) {
                        items.add("검색한 학교가 없습니다.")
                        _universityResult.value = items
                    } catch (e: IndexOutOfBoundsException) {
                        Log.d("XXX", "XXX")
                    }

                } else {
                    Log.d("NULLTEST", "NULLTEST" + response.raw())
                }
                _universityResult.value = items
                Log.d("CODE", response.code().toString())
            }

            override fun onFailure(call: Call<SearchUniversityResult>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}