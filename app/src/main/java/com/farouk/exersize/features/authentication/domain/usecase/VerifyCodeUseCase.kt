package com.farouk.exersize.features.authentication.domain.usecase

import com.farouk.exersize.features.authentication.data.Model.codeVerfication.VerifyCodeModel
import com.farouk.exersize.features.authentication.data.Model.codeVerfication.VerifyCodeResponse
import com.farouk.exersize.features.authentication.domain.repository.AuthRepo
import com.farouk.exersize.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class VerifyCodeUseCase @Inject constructor(
    private val authRepo: AuthRepo
) {
    operator fun invoke(verifyCodeModel: VerifyCodeModel): Flow<Resource<VerifyCodeResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = authRepo.verifyCode(verifyCodeModel)
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "un excepted error occurred", null))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server . check your internet connection ", null))
        }
    }
}