package com.commit.egusajo.data.remote

import com.commit.egusajo.data.model.MyInfoResponse
import com.commit.egusajo.data.model.PatchMyInfoRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UserApi {

    @GET("/users/me")
    suspend fun getMyInfo(): Response<MyInfoResponse>

    @PATCH("/users/me")
    suspend fun patchMyInfo(
        @Body params: PatchMyInfoRequest
    ): Response<Unit>

    @DELETE("/users/me")
    suspend fun withdrawal(): Response<Unit>
}