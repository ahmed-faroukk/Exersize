package com.farouk.exersize.features.authentication.di

import com.farouk.exersize.base.Constants.BASE_URL
import com.farouk.exersize.features.inbody.data.remote.InBodyApiInterface
import com.farouk.exersize.features.inbody.data.repo.InBodyRepoImpl
import com.farouk.exersize.features.inbody.domain.repo.InBodyRepo
import com.farouk.exersize.features.inbody.domain.usecases.InBodyUseCase
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
object InBodyModule {

    // create retrofit clint
    @Provides
    @Singleton
    fun provideApi(): InBodyApiInterface {
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

        return retrofit.create(InBodyApiInterface::class.java)

    }

    // inject clintInterface into repo
    @Provides
    @Singleton
    fun provideRepo(api: InBodyApiInterface): InBodyRepo {
        return InBodyRepoImpl(api)
    }


    // inject repo into use-case
    @Provides
    @Singleton
    fun provideAuthUseCases(repo: InBodyRepo) = InBodyUseCase(repo)
}