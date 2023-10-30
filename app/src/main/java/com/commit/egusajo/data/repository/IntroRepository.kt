package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.LoginRequest
import com.commit.egusajo.data.model.LoginResponse
import com.commit.egusajo.data.model.NickCheckRequest
import com.commit.egusajo.data.model.NickCheckResponse
import com.commit.egusajo.data.model.SignupRequest
import retrofit2.Response

interface IntroRepository {

    suspend fun login(
        data: LoginRequest
    ): Response<LoginResponse>

    suspend fun signup(
        data: SignupRequest
    ): Response<LoginResponse>

    suspend fun checkNick(
        data: NickCheckRequest
    ): Response<NickCheckResponse>

}