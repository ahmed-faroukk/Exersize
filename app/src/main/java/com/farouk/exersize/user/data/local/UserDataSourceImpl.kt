package com.farouk.exersize.user.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.farouk.exersize.user.data.model.UserDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class UserDataSourceImpl(
    private val context: Context,
) : UserLocalDataSource {
    companion object {
        val USER_ID = stringPreferencesKey("user_Id")
        val CHAT_ID = stringPreferencesKey("chat_Id")
        val LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
        val USER_KEY = stringPreferencesKey("user")
        val USER_SETTINGS = "user_settings"
        val USER_TOKEN = stringPreferencesKey("user_Token")
    }

    private val Context.dataStore by preferencesDataStore(
        name = USER_SETTINGS
    )

    override suspend fun setLoggedIn() {
        context.dataStore.edit {
            it[LOGGED_IN_KEY] = true
        }
    }

    override suspend fun setLoggedout() {
        context.dataStore.edit {
            it[LOGGED_IN_KEY] = false
        }
    }

    override suspend fun isLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[LOGGED_IN_KEY] ?: false
        }
    }

    override suspend fun saveData(userDataModel: UserDataModel) {
        context.dataStore.edit { preferences ->
            preferences[USER_KEY] = userDataModel.toString() // Convert UserDataModel to String
        }
        Log.d("data saved ", userDataModel.token)
    }

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN] = token  // Convert UserDataModel to String
        }
        Log.d("data saved ", token)
    }

    override suspend fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            val userDataJsonString = preferences[USER_TOKEN]
            preferences[USER_TOKEN]
        }
    }

    override suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId  // Convert UserDataModel to String
        }
        Log.d("data saved ", userId)
    }

    override suspend fun getUserId(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            val userDataJsonString = preferences[USER_ID]
            preferences[USER_ID]
        }
    }

    override suspend fun saveChatUserId(chatId: String) {
        context.dataStore.edit { preferences ->
            preferences[CHAT_ID] = chatId  // Convert UserDataModel to String
        }
        Log.d("data saved ", chatId)
    }

    override suspend fun getChatId(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            val userDataJsonString = preferences[CHAT_ID]
            preferences[CHAT_ID]
        }
    }


    override suspend fun getData(): Flow<UserDataModel?> {
        return context.dataStore.data.map { preferences ->
            val userDataJsonString = preferences[USER_KEY]
            userDataJsonString?.let {
                try {
                    Json.decodeFromString<UserDataModel>(it)
                } catch (e: Exception) {
                    // Handle the exception (e.g., log or return null)
                    null
                }
            }
        }
    }
}