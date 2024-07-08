package com.farouk.exersize.features.home.presentaion.getCoach.states

import com.farouk.exersize.features.home.domain.entity.CoachByIdResponse

data class GetCoachState(
    val data : CoachByIdResponse?= null,
    val isLoading : Boolean = false,
    val error : String = ""
)
