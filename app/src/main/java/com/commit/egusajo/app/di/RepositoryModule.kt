package com.commit.egusajo.app.di

import com.commit.egusajo.data.repository.HomeRepository
import com.commit.egusajo.data.repository.HomeRepositoryImpl
import com.commit.egusajo.data.repository.ImageRepository
import com.commit.egusajo.data.repository.ImageRepositoryImpl
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
        introRepositoryImpl: IntroRepositoryImpl
    ): IntroRepository

    @Singleton
    @Binds
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository

    @Singleton
    @Binds
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository
}