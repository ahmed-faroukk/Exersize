package com.farouk.exersize.features.menu.profile.domain.usecases

import com.farouk.exersize.features.menu.profile.domain.Entity.UnsubscribeResponse
import com.farouk.exersize.features.menu.profile.domain.repo.ProfileRepo
import com.farouk.exersize.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UnsubscribeUseCase  @Inject constructor(
    private val profileRepo: ProfileRepo
) {
    operator fun invoke(
        token: String,
    ): Flow<Resource<UnsubscribeResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = profileRepo.unsubscribe(token)
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "un excepted error occurred", null))
        } catch (e: IOException) {
            emit(Resource.Error(e.message, null))
        }
    }
}