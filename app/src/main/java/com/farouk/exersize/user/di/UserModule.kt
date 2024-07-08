package com.farouk.exersize.user.di

import android.content.Context
import com.farouk.exersize.user.data.local.UserDataSourceImpl
import com.farouk.exersize.user.data.local.UserLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    @Provides
    @Singleton
    fun provideRepo(@ApplicationContext context: Context) : UserLocalDataSource = UserDataSourceImpl(context)

}
