package com.farouk.exersize.features.plan.domain.repo

import com.farouk.exersize.features.plan.domain.Entitiy.GetPlanResponse

interface PlanRepo  {
    suspend fun getTraineePlan( token : String) : GetPlanResponse

}