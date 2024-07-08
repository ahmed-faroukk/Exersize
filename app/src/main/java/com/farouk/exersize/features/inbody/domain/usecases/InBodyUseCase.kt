package com.farouk.exersize.features.inbody.domain.usecases

import com.farouk.exersize.features.inbody.domain.Entity.InBodyResponse
import com.farouk.exersize.features.inbody.domain.repo.InBodyRepo
import com.farouk.exersize.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class InBodyUseCase @Inject constructor(
    private val inBodyRepo: InBodyRepo
) {
    operator fun invoke(
        gender: RequestBody,
        age: RequestBody,
        weight: RequestBody,
        tall: RequestBody,
        token: RequestBody,
        inbody_pdf: MultipartBody.Part?,
        img: MultipartBody.Part
    ): Flow<Resource<InBodyResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = inBodyRepo.sendInBodyData(gender, age, weight, tall, token, inbody_pdf, img)
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "un excepted error occurred", null))
        } catch (e: IOException) {
            emit(Resource.Error(e.message, null))
        }
    }
}