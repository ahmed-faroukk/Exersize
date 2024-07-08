package com.farouk.exersize.features.inbody.data.remote

import com.farouk.exersize.features.authentication.common.InBodyConstants
import com.farouk.exersize.features.inbody.domain.Entity.InBodyResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface InBodyApiInterface {

    @Multipart
    @POST(InBodyConstants.INBODY_ENDPOINT)
    suspend fun sendInBodyData(
        @Part("gender") gender : RequestBody ,
        @Part("age") age : RequestBody ,
        @Part("weight") weight : RequestBody ,
        @Part("tall") tall : RequestBody ,
        @Part("token") token : RequestBody ,
        @Part inbody_pdf : MultipartBody.Part? ,
        @Part img : MultipartBody.Part ,
    ) : InBodyResponse


}