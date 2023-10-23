package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.LoginRequest
import com.commit.egusajo.data.model.LoginResponse
import retrofit2.Response

interface IntroRepository {

    suspend fun login(
        data: LoginRequest
    ): Response<LoginResponse>
}