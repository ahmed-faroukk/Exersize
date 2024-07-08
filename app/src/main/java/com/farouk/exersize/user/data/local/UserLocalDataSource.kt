package com.farouk.exersize.user.data.local

import com.farouk.exersize.user.data.model.UserDataModel
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

    suspend fun setLoggedIn()
    suspend fun setLoggedout()
    suspend fun isLoggedIn(): Flow<Boolean>
    suspend fun saveData(userDataModel: UserDataModel)
    suspend fun getData(): Flow<UserDataModel?>
    suspend fun saveToken(token: String)
    suspend fun getToken(): Flow<String?>
    suspend fun saveUserId(userId: String)
    suspend fun getUserId(): Flow<String?>
    suspend fun saveChatUserId(chatId: String)
    suspend fun getChatId(): Flow<String?>


}