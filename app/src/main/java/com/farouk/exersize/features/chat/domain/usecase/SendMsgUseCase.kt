package com.farouk.exersize.features.chat.domain.usecase

import android.media.session.MediaSession
import com.farouk.exersize.features.chat.domain.Entity.SendMsgResponse
import com.farouk.exersize.features.chat.domain.repo.ChatRepo
import com.farouk.exersize.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SendMsgUseCase @Inject constructor(
    private val repo: ChatRepo,
) {
    operator fun invoke(
        chat_id: String,
        message: String,
        token: String,
    ): Flow<Resource<SendMsgResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = repo.sendMsg(chat_id , message , token)
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "server exception" + e.message.toString(), data = null))
        } catch (e: IOException) {
            emit(Resource.Error(message = "check your internet connection ", data = null))
        }

    }

}