package com.farouk.exersize.features.menu.profile.presentaion

import com.farouk.exersize.features.menu.profile.domain.Entity.EditeProfileResponse

data class UpdateProfileState(
    val data : EditeProfileResponse?= null,
    val isLoading : Boolean = false,
    val error : String = ""
)
