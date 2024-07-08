package com.farouk.exersize.features.home.di

import com.farouk.exersize.base.Constants.BASE_URL
import com.farouk.exersize.features.home.data.remote.HomeApiInterface
import com.farouk.exersize.features.home.data.repo.HomeRepoImpl
import com.farouk.exersize.features.home.domain.repo.HomeRepo
import com.farouk.exersize.features.home.domain.usecase.GetAllCoachesUseCase
import com.farouk.exersize.features.home.domain.usecase.GetCoachUseCase
import com.farouk.exersize.features.home.domain.usecase.GetPackagesUseCase
import com.farouk.exersize.features.home.domain.usecase.GetPortfolioUseCase
import com.farouk.exersize.features.home.domain.usecase.HomeUseCases
import com.farouk.exersize.features.home.domain.usecase.ReqPackageUseCase
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
object HomeModule {
    @Provides
    @Singleton
    fun provideApi(): HomeApiInterface {
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

        return retrofit.create(HomeApiInterface::class.java)

    }

    // inject clintInterface into repo
    @Provides
    @Singleton
    fun provideRepo(api: HomeApiInterface): HomeRepo {
        return HomeRepoImpl(api)
    }


    // inject repo into use-case
    @Provides
    @Singleton
    fun provideAuthUseCases(homeRepo: HomeRepo) = HomeUseCases(
        GetAllCoachesUseCase(homeRepo),
        GetCoachUseCase(homeRepo),
        GetPackagesUseCase(homeRepo),
        ReqPackageUseCase(homeRepo),
        GetPortfolioUseCase(homeRepo)
    )
}