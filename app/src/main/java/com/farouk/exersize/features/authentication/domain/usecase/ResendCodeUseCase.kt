package com.farouk.exersize.features.authentication.domain.usecase

import com.farouk.exersize.features.authentication.data.Model.codeVerfication.ResendCodeResponse
import com.farouk.exersize.features.authentication.domain.entity.login.UserLoginModel
import com.farouk.exersize.features.authentication.domain.entity.login.UserLoginResponse
import com.farouk.exersize.features.authentication.domain.repository.AuthRepo
import com.farouk.exersize.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ResendCodeUseCase @Inject constructor(
    private val authRepo: AuthRepo
) {
    operator fun invoke(phone : String): Flow<Resource<ResendCodeResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = authRepo.resendCode(phone)
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "un excepted error occurred", null))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server . check your internet connection ", null))
        }
    }
}