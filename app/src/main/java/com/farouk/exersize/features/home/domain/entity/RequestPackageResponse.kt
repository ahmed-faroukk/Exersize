package com.farouk.exersize.features.home.domain.entity

data class RequestPackageResponse(
    val error_msg: String,
    val msg: List<Any>,
    val status: Boolean
)