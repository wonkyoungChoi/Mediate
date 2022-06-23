package com.wk.mediate.data.api

import com.wk.mediate.data.models.SearchSchoolResult
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query


//초, 중, 고등학교 정보조회
interface SchoolInfoApi {
    @POST("hub/schoolInfo")
    suspend fun loadSchool(@Query("Key") key:String, @Query("Type") type: String, @Query("SCHUL_NM") name: String): Response<SearchSchoolResult>
}

//interface ImageUploadService {
//    @Multipart
//    @POST("api/profile-image")
//    fun loadImageUpload(@Part file: MultipartBody.Part): Response<ProfileImageResult>
//}

//object BasicAPI {
//
//    //프로필 이미지 업로드
//    fun createProfileImageApi(): ImageUploadService {
//        Log.d("PrefAccess", Pref.auth?.accessToken.toString())
//        return oAuthRetrofit.create(
//            ImageUploadService::class.java
//        )
//    }
//}
