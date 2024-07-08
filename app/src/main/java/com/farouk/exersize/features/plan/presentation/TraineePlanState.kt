package com.farouk.exersize.features.plan.presentation

import com.farouk.exersize.features.plan.domain.Entitiy.GetPlanResponse

data class TraineePlanState(
    val data : GetPlanResponse?= null,
    val isLoading : Boolean = false,
    val errorMsg : String = ""
)
