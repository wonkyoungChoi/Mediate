package com.wk.mediate.network

import com.wk.mediate.ui.Login.Login
import com.wk.mediate.ui.Login.LoginResult
import com.wk.mediate.ui.Register.Select.Area.SelectAreaResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface AreaService {
    @GET("v1/regcodes")
    fun loadArea(@Query("regcode_pattern") regcode:String, @Query("is_ignore_zero") ignore:String): Call<SelectAreaResult>
}

object AreaApi {
    private const val baseUrl = "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun createApi(): AreaService {
        return retrofit.create(
            AreaService::class.java)
    }
}