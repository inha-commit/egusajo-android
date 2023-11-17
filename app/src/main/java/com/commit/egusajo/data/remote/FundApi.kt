package com.commit.egusajo.data.remote

import com.commit.egusajo.data.model.CreateFundRequest
import com.commit.egusajo.data.model.FundDetailResponse
import com.commit.egusajo.data.model.FundListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FundApi {

    @GET("/presents")
    suspend fun getFundList(
        @Query("page") page: Int
    ): Response<FundListResponse>

    @GET("/presents/{presentId}")
    suspend fun getFundDetail(
        @Path("presentId") presentId: Int
    ): Response<FundDetailResponse>

    @POST("/presents")
    suspend fun createFund(
        @Body params: CreateFundRequest
    ): Response<Unit>
}