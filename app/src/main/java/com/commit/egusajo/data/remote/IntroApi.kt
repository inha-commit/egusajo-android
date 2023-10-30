package com.commit.egusajo.data.remote

import com.commit.egusajo.data.model.LoginRequest
import com.commit.egusajo.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IntroApi {

    @POST("/auth/sign-in")
    suspend fun login(
        @Body params: LoginRequest
    ) : Response<LoginResponse>
}