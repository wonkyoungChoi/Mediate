package com.wk.mediate.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wk.mediate.BuildConfig
import com.wk.mediate.network.SchoolApi
import com.wk.mediate.network.UniversityApi
import com.wk.mediate.ui.Register.Search.SearchMajorResult
import com.wk.mediate.ui.Register.Search.SearchSchoolResult
import com.wk.mediate.ui.Register.Search.SearchUniversityResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchSchoolRepository {
    var _schoolResult = MutableLiveData<List<String>>()
    var _universityResult = MutableLiveData<List<String>>()
    var _majorResult = MutableLiveData<List<String>>()


    fun loadSchoolResult(search: String) {
        val call = SchoolApi.createApi().loadSchool(BuildConfig.SCHOOL_API_KEY,"json", search)

        call.enqueue(object : Callback<SearchSchoolResult> {
            override fun onResponse(call: Call<SearchSchoolResult>, response: Response<SearchSchoolResult>) {
                val items = ArrayList<String>()
                val list: SearchSchoolResult? = response.body()

                if (list != null) {
                    try {

                        list.schoolInfo?.get(1)?.row?.forEachIndexed { index, s ->
                            if (index < 10) {
                                items.add(s.schulNm.toString())
                            }
                        }

                    } catch (e: NullPointerException) {
                        items.add("검색한 학교가 없습니다.")
                        _schoolResult.value = items
                    }
                }
                _schoolResult.value = items.distinct()
            }

            override fun onFailure(call: Call<SearchSchoolResult>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun loadUniversityResult(search: String) {
        val call = UniversityApi.createUnivApi().loadUniversity(BuildConfig.UNIVERSITY_API_KEY, "api","SCHOOL", "json", "univ_list", search)

        call.enqueue(object : Callback<SearchUniversityResult> {
            override fun onResponse(call: Call<SearchUniversityResult>, response: Response<SearchUniversityResult>) {
                Log.d("url", response.raw().toString())
                val items = ArrayList<String>()
                val list: SearchUniversityResult? = response.body()

                try {
                    if (list != null && list.dataSearch?.content?.get(0)  != null) {
                        list.dataSearch!!.content?.forEachIndexed { index, s ->
                            if (index < 10) {
                                items.add(s.schoolName.toString())
                            }
                        }
                    }
                } catch (e: NullPointerException) {
                    Log.d("NullCheck", "NullCheck")
                } catch (e: IndexOutOfBoundsException) {
                    items.add("검색한 학교가 없습니다.")
                    _universityResult.value = items
                }


                _universityResult.value = items.distinct()

            }

            override fun onFailure(call: Call<SearchUniversityResult>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun loadMajorResult(search: String) {
        val call = UniversityApi.createMajorApi().loadMajor(BuildConfig.UNIVERSITY_API_KEY, "api","MAJOR", "json", "univ_list", search)

        call.enqueue(object : Callback<SearchMajorResult> {
            override fun onResponse(call: Call<SearchMajorResult>, response: Response<SearchMajorResult>) {
                Log.d("url", response.raw().toString())
                val items = ArrayList<String>()
                val list: SearchMajorResult? = response.body()

                try {
                    if (list != null && list.dataSearch?.content?.get(0)  != null) {
                        list.dataSearch!!.content?.forEachIndexed { index, s ->
                            if (index < 10) {
                                items.add(s.mClass.toString())
                            }
                        }
                    }
                } catch (e: NullPointerException) {
                    Log.d("NullCheck", "NullCheck")
                } catch (e: IndexOutOfBoundsException) {
                    items.add("검색한 학과가 없습니다.")
                    _majorResult.value = items
                }

                _majorResult.value = items.distinct()

            }

            override fun onFailure(call: Call<SearchMajorResult>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

}