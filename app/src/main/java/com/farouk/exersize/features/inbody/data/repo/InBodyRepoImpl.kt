package com.farouk.exersize.features.inbody.data.repo

import com.farouk.exersize.features.inbody.data.remote.InBodyApiInterface
import com.farouk.exersize.features.inbody.domain.Entity.InBodyResponse
import com.farouk.exersize.features.inbody.domain.repo.InBodyRepo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class InBodyRepoImpl @Inject constructor(
    private val inBodyApiInterface: InBodyApiInterface
) : InBodyRepo {
    override suspend fun sendInBodyData(
        gender: RequestBody,
        age: RequestBody,
        weight: RequestBody,
        tall: RequestBody,
        token: RequestBody,
        inbody_pdf: MultipartBody.Part?,
        img: MultipartBody.Part
    ): InBodyResponse = inBodyApiInterface.sendInBodyData(gender, age, weight, tall, token, inbody_pdf, img)

}