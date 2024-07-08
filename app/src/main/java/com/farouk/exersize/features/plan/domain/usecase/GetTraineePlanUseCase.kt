package com.farouk.exersize.features.plan.domain.usecase

import com.farouk.exersize.features.plan.domain.Entitiy.GetPlanResponse
import com.farouk.exersize.features.plan.domain.repo.PlanRepo
import com.farouk.exersize.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTraineePlanUseCase @Inject constructor(
    private val repo : PlanRepo
){
    operator fun invoke (token : String) : Flow<Resource<GetPlanResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = repo.getTraineePlan(token)
            emit(Resource.Success(data = response))
        }catch (e : HttpException){
            emit(Resource.Error(message = "server exception" + e.message.toString() , data = null))
        }catch (e : IOException){
            emit(Resource.Error(message = "check your internet connection " , data = null))
        }

    }

}