package com.farouk.exersize.features.home.domain.entity

data class ShowCoachesResponse(
    val error_msg: String,
    val msg: List<Msg>,
    val status: Boolean
)