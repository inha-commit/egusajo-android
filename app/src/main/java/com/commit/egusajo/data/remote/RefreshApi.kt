package com.commit.egusajo.data.remote

import com.commit.egusajo.data.model.response.RefreshResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface RefreshApi {

    @POST("/auth/refresh")
    suspend fun refreshToken(
        @Header("refresh-token") refreshToken: String
    ): Response<RefreshResponse>

}