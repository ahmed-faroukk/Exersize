package com.farouk.exersize.features.menu.profile.domain.Entity

data class UnsubscribeResponse(
    val error_msg: String,
    val msg: List<Any>,
    val status: Boolean
)