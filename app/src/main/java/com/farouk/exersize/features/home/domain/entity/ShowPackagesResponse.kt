package com.farouk.exersize.features.home.domain.entity

data class ShowPackagesResponse(
    val error_msg: String,
    val msg: List<MsgXX>,
    val status: Boolean
)