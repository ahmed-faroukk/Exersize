package com.farouk.exersize.features.plan.data.repoImpl

import com.farouk.exersize.features.plan.data.remote.PlanApiInterface
import com.farouk.exersize.features.plan.domain.Entitiy.GetPlanResponse
import com.farouk.exersize.features.plan.domain.repo.PlanRepo
import javax.inject.Inject

class PlanRepoImpl @Inject constructor(

    private val apiInterface: PlanApiInterface,

    ) : PlanRepo {
    override suspend fun getTraineePlan(token: String): GetPlanResponse = apiInterface.getTraineePlan(token)

}