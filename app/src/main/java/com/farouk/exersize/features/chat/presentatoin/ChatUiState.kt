package com.farouk.exersize.features.chat.presentatoin

import com.farouk.exersize.features.chat.domain.Entity.MsgX
import com.farouk.exersize.features.chat.domain.Entity.SendMsgResponse
import com.farouk.exersize.features.chat.domain.Entity.ShowChatResponse
import com.farouk.exersize.features.chat.presentatoin.composables.Sender

data class ChatUiState(
    val data : ShowChatResponse?= null,
    val isLoading : Boolean = false,
    val errorMsg : String = ""
)

data class SendMsgUiState(
    val data : SendMsgResponse?= null,
    val isLoading : Boolean = false,
    val errorMsg : String = ""
)

data class Message(val content : String , val time : String , val senderIs : Sender)
data class TraineeChat(val message: List<MsgX>)