package com.farouk.exersize.features.chat.domain.usecase

import com.farouk.exersize.features.chat.domain.Entity.ShowChatResponse
import com.farouk.exersize.features.chat.domain.repo.ChatRepo
import com.farouk.exersize.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetChatUseCase @Inject constructor(
    private val repo : ChatRepo
){
    operator fun invoke (token : String) : Flow<Resource<ShowChatResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = repo.getChatHistory(token)
            emit(Resource.Success(data = response))
        }catch (e : HttpException){
            emit(Resource.Error(message = "server exception" + e.message.toString() , data = null))
        }catch (e : IOException){
            emit(Resource.Error(message = "check your internet connection " , data = null))
        }

    }

}