package com.farouk.exersize.features.home.presentaion.getAllCoaches

import com.farouk.exersize.features.home.domain.entity.ShowCoachesResponse

data class GetAllCoachesState(
    val data : ShowCoachesResponse?= null,
    val isLoading : Boolean = false,
    val error : String = ""
)
