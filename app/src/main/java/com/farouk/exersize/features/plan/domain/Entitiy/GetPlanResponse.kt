package com.farouk.exersize.features.plan.domain.Entitiy

import com.google.gson.annotations.SerializedName

data class GetPlanResponse(
    val coach: Coach,
    val error_msg: String,
    val id: Int,
    val msg: List<Msg>,
   @SerializedName("package") val paackage : Package,
    val payment_status: String,
    val status: Boolean
)