package com.commit.egusajo.data.remote

import com.commit.egusajo.data.model.request.LoginRequest
import com.commit.egusajo.data.model.response.LoginResponse
import com.commit.egusajo.data.model.request.NickCheckRequest
import com.commit.egusajo.data.model.response.NickCheckResponse
import com.commit.egusajo.data.model.request.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IntroApi {

    @POST("/auth/sign-in")
    suspend fun login(
        @Body params: LoginRequest
    ): Response<LoginResponse>

    @POST("/auth/sign-up")
    suspend fun signup(
        @Body params: SignupRequest
    ): Response<LoginResponse>

    @POST("/auth/nickname-validation")
    suspend fun checkNick(
        @Body params: NickCheckRequest
    ): Response<NickCheckResponse>
}