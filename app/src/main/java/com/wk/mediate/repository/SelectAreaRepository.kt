package com.wk.mediate.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wk.mediate.BuildConfig
import com.wk.mediate.network.AreaApi
import com.wk.mediate.network.SchoolApi
import com.wk.mediate.network.UniversityApi
import com.wk.mediate.ui.Register.Select.Area.SelectAreaResult
import com.wk.mediate.ui.Register.Select.School.Search.SearchMajorResult
import com.wk.mediate.ui.Register.Select.School.Search.SearchSchoolResult
import com.wk.mediate.ui.Register.Select.School.Search.SearchUniversityResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectAreaRepository {
    var _doResult = MutableLiveData<List<String>>()
    var _siGunGuResult = MutableLiveData<List<String>>()
    var _dongResult = MutableLiveData<List<String>>()

    fun loadDoResult(search: String) {
        val call = AreaApi.createApi().loadArea(search, "false")
        Log.d("LoadURL", call.request().url().toString())
        call.enqueue(object : Callback<SelectAreaResult> {
            override fun onResponse(call: Call<SelectAreaResult>, response: Response<SelectAreaResult>) {
                val items = ArrayList<String>()
                val list: SelectAreaResult? = response.body()

                if (list != null) {
                    for(i in list.regcodes.indices) {
                        if(list.regcodes[i].name.contains("북") || list.regcodes[i].name.contains("남")) {
                            items.add(list.regcodes[i].name[0] + list.regcodes[i].name[2].toString())
                        } else items.add(list.regcodes[i].name.substring(0, 2))
                    }
                }
                _doResult.value = items
            }

            override fun onFailure(call: Call<SelectAreaResult>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun loadSiGunGuResult(search: String) {
        val call = AreaApi.createApi().loadArea(search, "false")

        call.enqueue(object : Callback<SelectAreaResult> {
            override fun onResponse(call: Call<SelectAreaResult>, response: Response<SelectAreaResult>) {
                val items = ArrayList<String>()
                val list: SelectAreaResult? = response.body()

                if (list != null) {
                    for(i in list.regcodes.indices) {
                        items.add(list.regcodes[i].name)
                    }
                }
                _siGunGuResult.value = items
            }

            override fun onFailure(call: Call<SelectAreaResult>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun loadDongResult(search: String) {
        val call = AreaApi.createApi().loadArea(search, "false")

        call.enqueue(object : Callback<SelectAreaResult> {
            override fun onResponse(call: Call<SelectAreaResult>, response: Response<SelectAreaResult>) {
                val items = ArrayList<String>()
                val list: SelectAreaResult? = response.body()

                if (list != null) {
                    for(i in list.regcodes.indices) {
                        items.add(list.regcodes[i].name.trim().replace(list.regcodes[0].name, ""))
                    }
                }
                _dongResult.value = items
            }

            override fun onFailure(call: Call<SelectAreaResult>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}