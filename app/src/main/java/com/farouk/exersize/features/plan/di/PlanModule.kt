package com.farouk.exersize.features.plan.di

import com.farouk.exersize.base.Constants.BASE_URL
import com.farouk.exersize.features.plan.data.remote.PlanApiInterface
import com.farouk.exersize.features.plan.data.repoImpl.PlanRepoImpl
import com.farouk.exersize.features.plan.domain.repo.PlanRepo
import com.farouk.exersize.features.plan.domain.usecase.GetTraineePlanUseCase
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
object PlanModule {

    @Provides
    @Singleton
    fun provideApi(): PlanApiInterface {
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

        return retrofit.create(PlanApiInterface::class.java)

    }

    @Provides
    @Singleton
    fun provideRepo(api : PlanApiInterface) : PlanRepo = PlanRepoImpl(api)

    // inject repo into use-case
    @Provides
    @Singleton
    fun providePlanUseCase(repo: PlanRepo) : GetTraineePlanUseCase = GetTraineePlanUseCase(repo)
}