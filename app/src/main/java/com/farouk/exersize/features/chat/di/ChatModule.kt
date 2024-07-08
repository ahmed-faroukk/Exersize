package com.farouk.exersize.features.chat.di

import com.farouk.exersize.base.Constants.BASE_URL
import com.farouk.exersize.features.chat.data.remote.ChatApiInterface
import com.farouk.exersize.features.chat.data.repoImpl.ChatRepoImpl
import com.farouk.exersize.features.chat.domain.repo.ChatRepo
import com.farouk.exersize.features.chat.domain.usecase.ChatUseCases
import com.farouk.exersize.features.chat.domain.usecase.GetChatUseCase
import com.farouk.exersize.features.chat.domain.usecase.SendMsgUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {


    // create retrofit clint
    @Provides
    @Singleton
    fun provideApi(): ChatApiInterface {
        val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(logging).build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        return retrofit.create(ChatApiInterface::class.java)

    }

    // inject clintInterface into repo
    @Provides
    @Singleton
    fun provideRepo(api: ChatApiInterface): ChatRepo {
        return ChatRepoImpl(api)
    }


    // inject repo into use-case
    @Provides
    @Singleton
    fun provideAuthUseCases(chatRepo: ChatRepo) = ChatUseCases(
        SendMsgUseCase(chatRepo),
        GetChatUseCase(chatRepo)
    )
}
