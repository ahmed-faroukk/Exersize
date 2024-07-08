package com.farouk.exersize.features.home.domain.usecase

import com.farouk.exersize.features.home.domain.entity.ShowCoachesResponse
import com.farouk.exersize.features.home.domain.repo.HomeRepo
import com.farouk.exersize.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllCoachesUseCase @Inject constructor(
    private val repo: HomeRepo
) {
    operator fun invoke(token : String): Flow<Resource<ShowCoachesResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repo.getAllCoaches(token)
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "un excepted error occurred", null))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server . check your internet connection ", null))
        }
    }
}