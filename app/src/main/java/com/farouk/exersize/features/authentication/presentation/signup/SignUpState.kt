package com.farouk.exersize.features.authentication.presentation.signup

import com.farouk.exersize.features.authentication.domain.entity.login.UserLoginResponse
import com.farouk.exersize.features.authentication.domain.entity.signup.UserSignupResponse

data class SignUpState(
    val data : UserSignupResponse ?= null   ,
    val isLoading : Boolean = false ,
    val error : String = ""
)
