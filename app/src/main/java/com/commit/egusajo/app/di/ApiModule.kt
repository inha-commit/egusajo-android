package com.commit.egusajo.app.di

import com.commit.egusajo.data.remote.IntroApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideIntroService(retrofit: Retrofit): IntroApi {
        return retrofit.create(IntroApi::class.java)
    }
}