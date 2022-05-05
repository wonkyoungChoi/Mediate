package com.wk.mediate.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wk.mediate.network.AreaApi
import com.wk.mediate.ui.Register.Select.Area.AreaItem
import com.wk.mediate.ui.Register.Select.Area.SelectAreaResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectAreaRepository {
    var _doResult = MutableLiveData<List<String>>()
    var _siGunGuResult = MutableLiveData<List<AreaItem>>()
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
                        items.add(reName(list.regcodes[i].name))
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
                val items = ArrayList<AreaItem>()
                val list: SelectAreaResult? = response.body()

                if (list != null) {
                    for(i in list.regcodes.indices) {
                        if(i == 0) {
                            items.add(AreaItem(reName(list.regcodes[i].name) + " 전체"))
                        } else items.add(AreaItem(list.regcodes[i].name.replace(list.regcodes[0].name, "").trim()))
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

    private fun reName(name: String): String {
        return if(name.contains("북") || name.contains("남")) {
            name[0] + name[2].toString()
        } else name.substring(0, 2)
    }

}