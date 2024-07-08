package com.farouk.exersize.features.inbody.presentation

import com.farouk.exersize.features.inbody.domain.Entity.InBodyResponse

data class UserInBodyState(
    val data : InBodyResponse ?= null   ,
    val isLoading : Boolean = false ,
    val error : String = ""
)
