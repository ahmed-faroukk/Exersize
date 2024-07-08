package com.farouk.exersize.features.chat.domain.Entity

data class SendMsgResponse(
    val error_msg: String,
    val msg: Msg,
    val status: Boolean
)