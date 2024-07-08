package com.farouk.exersize.features.authentication.presentation.login

import com.farouk.exersize.features.authentication.domain.entity.login.UserLoginResponse

data class LoginState(
    val data : UserLoginResponse ?= null  ,
    val isLoading : Boolean = false ,
    val error : String = ""
)
