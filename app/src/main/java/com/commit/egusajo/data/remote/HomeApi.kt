package com.commit.egusajo.data.remote

import com.commit.egusajo.data.model.FundListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("/presents")
    suspend fun getFundList(
        @Query("page") page: Int
    ): Response<FundListResponse>
}