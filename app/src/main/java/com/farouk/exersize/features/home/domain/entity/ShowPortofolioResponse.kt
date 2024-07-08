package com.farouk.exersize.features.home.domain.entity

data class ShowPortofolioResponse(
    val error_msg: String,
    val msg: List<MsgXXX>,
    val status: Boolean
)