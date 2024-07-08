package com.farouk.exersize.features.chat.data.repoImpl

import com.farouk.exersize.features.chat.data.remote.ChatApiInterface
import com.farouk.exersize.features.chat.domain.Entity.SendMsgResponse
import com.farouk.exersize.features.chat.domain.Entity.ShowChatResponse
import com.farouk.exersize.features.chat.domain.repo.ChatRepo
import javax.inject.Inject

class ChatRepoImpl @Inject constructor(
    val apiInterface: ChatApiInterface
) : ChatRepo {
    override suspend fun sendMsg(
        chat_id: String,
        message: String,
        token: String,
    ): SendMsgResponse =apiInterface.sendMsg(chat_id,message,token)

    override suspend fun getChatHistory( token: String): ShowChatResponse = apiInterface.getChatHistory(token)


}