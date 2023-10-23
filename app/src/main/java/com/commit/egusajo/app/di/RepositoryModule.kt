package com.commit.egusajo.app.di

import com.commit.egusajo.data.repository.IntroRepository
import com.commit.egusajo.data.repository.IntroRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindIntroRepository(
        loginRepositoryImpl: IntroRepositoryImpl
    ): IntroRepository
}