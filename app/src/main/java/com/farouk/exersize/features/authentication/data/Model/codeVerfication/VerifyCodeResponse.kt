package com.farouk.exersize.features.authentication.data.Model.codeVerfication

data class VerifyCodeResponse(
    val status: Boolean,
    val error_msg: String,
    val msg: MsgXXXX,
)