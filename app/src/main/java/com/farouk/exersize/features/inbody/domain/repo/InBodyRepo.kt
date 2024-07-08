package com.farouk.exersize.features.inbody.domain.repo

import com.farouk.exersize.features.inbody.domain.Entity.InBodyResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface InBodyRepo {

    suspend fun sendInBodyData(
        gender: RequestBody,
        age: RequestBody,
        weight: RequestBody,
        tall: RequestBody,
        token: RequestBody,
        inbody_pdf: MultipartBody.Part?,
        img: MultipartBody.Part,
    ): InBodyResponse


}