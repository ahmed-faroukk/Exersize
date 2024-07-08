package com.farouk.exersize.features.chat.domain.repo

import com.farouk.exersize.features.chat.domain.Entity.SendMsgResponse
import com.farouk.exersize.features.chat.domain.Entity.ShowChatResponse

interface ChatRepo {

    suspend fun sendMsg (
        chat_id: String,
        message: String,
        token: String,
    ) : SendMsgResponse

    suspend fun getChatHistory (
        token: String,
    ) : ShowChatResponse

}