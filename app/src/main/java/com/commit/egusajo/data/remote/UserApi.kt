package com.commit.egusajo.data.remote

import com.commit.egusajo.data.model.response.FundListResponse
import com.commit.egusajo.data.model.response.MyInfoResponse
import com.commit.egusajo.data.model.response.MyParticipateResponse
import com.commit.egusajo.data.model.request.PatchMyInfoRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface UserApi {

    @GET("/users/me")
    suspend fun getMyInfo(): Response<MyInfoResponse>

    @PATCH("/users/me")
    suspend fun patchMyInfo(
        @Body params: PatchMyInfoRequest
    ): Response<Unit>

    @DELETE("/users/me")
    suspend fun withdrawal(): Response<Unit>

    @GET("/presents/me")
    suspend fun getMyFund(
        @Query("page") page: Int 
    ): Response<FundListResponse>

    @GET("/funds/me")
    suspend fun getMyParticipate(
        @Query("page") page: Int
    ): Response<MyParticipateResponse>
}