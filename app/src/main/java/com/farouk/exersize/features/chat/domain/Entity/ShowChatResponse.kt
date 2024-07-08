package com.farouk.exersize.features.chat.domain.Entity

data class ShowChatResponse(
    val error_msg: String,
    val msg: List<MsgX>,
    val status: Boolean,
    val chat_id : String
)