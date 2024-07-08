package com.farouk.exersize.features.plan.data.remote

import com.farouk.exersize.features.plan.domain.Entitiy.GetPlanResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PlanApiInterface {
    companion object {
            const val GET_USER_PLAN = "api/plan/every"
    }

    @POST(GET_USER_PLAN)
    @FormUrlEncoded
    suspend fun getTraineePlan(@Field("token") token : String) : GetPlanResponse

}