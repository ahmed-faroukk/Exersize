package com.farouk.exersize.features.menu.profile.domain.usecases

import com.farouk.exersize.features.menu.profile.domain.Entity.EditeProfileResponse
import com.farouk.exersize.features.menu.profile.domain.repo.ProfileRepo
import com.farouk.exersize.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateProfileUseCase  @Inject constructor(
    private val profileRepo: ProfileRepo
) {
    operator fun invoke(
        fname: RequestBody,
        lname: RequestBody,
        phone: RequestBody,
        img: MultipartBody.Part,
        token: RequestBody,
    ): Flow<Resource<EditeProfileResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = profileRepo.updateProfile(fname, lname, phone, img, token)
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "un excepted error occurred", null))
        } catch (e: IOException) {
            emit(Resource.Error(e.message, null))
        }
    }
}