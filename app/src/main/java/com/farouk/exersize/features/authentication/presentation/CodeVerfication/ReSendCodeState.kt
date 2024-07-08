package com.farouk.exersize.features.authentication.presentation.CodeVerfication

import com.farouk.exersize.features.authentication.data.Model.codeVerfication.ResendCodeResponse

data class ReSendCodeState(
    val data : ResendCodeResponse ?= null   ,
    val isLoading : Boolean = false ,
    val error : String = ""
)
