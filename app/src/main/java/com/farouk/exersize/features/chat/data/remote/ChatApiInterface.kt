package com.farouk.exersize.features.chat.data.remote

import com.farouk.exersize.features.chat.domain.Entity.SendMsgResponse
import com.farouk.exersize.features.chat.domain.Entity.ShowChatResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ChatApiInterface {
    companion object {
        const val SEND_MESSAGE = "api/trainee/chat"
        const val GET_CHAT = "api/chat"
    }

    @POST(SEND_MESSAGE)
    @FormUrlEncoded
    suspend fun sendMsg(
        @Field("chat_id") chat_id: String,
        @Field("message") message: String,
        @Field("token") token: String,
    ): SendMsgResponse

    @POST(GET_CHAT)
    @FormUrlEncoded
    suspend fun getChatHistory(
        @Field("token") token: String,
    ): ShowChatResponse


}