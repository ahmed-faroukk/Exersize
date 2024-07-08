package com.farouk.exersize.features.home.presentaion.getCoach.states

import com.farouk.exersize.features.home.domain.entity.ShowPortofolioResponse

data class GetPortfolioState(
    val data : ShowPortofolioResponse?= null,
    val isLoading : Boolean = false,
    val error : String = ""
)
