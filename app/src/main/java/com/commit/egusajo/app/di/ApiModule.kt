package com.commit.egusajo.app.di

import com.commit.egusajo.data.remote.FollowApi
import com.commit.egusajo.data.remote.FundApi
import com.commit.egusajo.data.remote.ImageApi
import com.commit.egusajo.data.remote.IntroApi
import com.commit.egusajo.data.remote.UserApi
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

    @Singleton
    @Provides
    fun provideImageService(retrofit: Retrofit): ImageApi {
        return retrofit.create(ImageApi::class.java)
    }

    @Singleton
    @Provides
    fun provideFundService(retrofit: Retrofit): FundApi {
        return retrofit.create(FundApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideFollowService(retrofit: Retrofit): FollowApi{
        return retrofit.create(FollowApi::class.java)
    }
}