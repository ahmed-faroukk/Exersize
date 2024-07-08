package com.farouk.exersize.features.menu.profile.di


import com.farouk.exersize.base.Constants.BASE_URL
import com.farouk.exersize.features.menu.profile.data.remote.ProfileApiInterface
import com.farouk.exersize.features.menu.profile.data.repo.ProfileRepoImpl
import com.farouk.exersize.features.menu.profile.domain.repo.ProfileRepo
import com.farouk.exersize.features.menu.profile.domain.usecases.ProfileUseCases
import com.farouk.exersize.features.menu.profile.domain.usecases.UnsubscribeUseCase
import com.farouk.exersize.features.menu.profile.domain.usecases.UpdateProfileUseCase
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
object ProfileModule {

    // create retrofit clint
    @Provides
    @Singleton
    fun provideApi(): ProfileApiInterface {
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
        return retrofit.create(ProfileApiInterface::class.java)
    }

    // inject clintInterface into repo
    @Provides
    @Singleton
    fun provideRepo(api: ProfileApiInterface): ProfileRepo {
        return ProfileRepoImpl(api)
    }


    // inject repo into use-case
    @Provides
    @Singleton
    fun provideAuthUseCases(repo: ProfileRepo) = ProfileUseCases(
        UpdateProfileUseCase(repo),
        UnsubscribeUseCase(repo)
    )
}