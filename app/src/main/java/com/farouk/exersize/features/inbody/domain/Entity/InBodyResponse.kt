package com.farouk.exersize.features.inbody.domain.Entity

data class InBodyResponse(
    val error_msg: String,
    val msg: Msg,
    val status: Boolean
)