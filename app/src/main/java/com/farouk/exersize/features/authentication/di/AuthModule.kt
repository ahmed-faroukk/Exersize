package com.farouk.exersize.features.authentication.di

import com.farouk.exersize.base.Constants.BASE_URL
import com.farouk.exersize.features.authentication.data.remote.AuthApiInterface
import com.farouk.exersize.features.authentication.data.repository.AuthRepoImpl
import com.farouk.exersize.features.authentication.domain.repository.AuthRepo
import com.farouk.exersize.features.authentication.domain.usecase.AuthUseCase
import com.farouk.exersize.features.authentication.domain.usecase.ResendCodeUseCase
import com.farouk.exersize.features.authentication.domain.usecase.UserLoginUseCase
import com.farouk.exersize.features.authentication.domain.usecase.UserSignupUseCase
import com.farouk.exersize.features.authentication.domain.usecase.VerifyCodeUseCase
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
object AuthModule {

    // create retrofit clint
    @Provides
    @Singleton
    fun provideApi(): AuthApiInterface {
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

        return retrofit.create(AuthApiInterface::class.java)

    }

    // inject clintInterface into repo
    @Provides
    @Singleton
    fun provideRepo(api: AuthApiInterface): AuthRepo {
        return AuthRepoImpl(api)
    }


    // inject repo into use-case
    @Provides
    @Singleton
    fun provideAuthUseCases(authRepo: AuthRepo) = AuthUseCase(
        UserLoginUseCase(authRepo),
        UserSignupUseCase(authRepo),
        ResendCodeUseCase(authRepo),
        VerifyCodeUseCase(authRepo)
    )
}