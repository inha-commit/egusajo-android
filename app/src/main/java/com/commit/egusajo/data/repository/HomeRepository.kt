package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.FundListResponse
import retrofit2.Response

interface HomeRepository {

    suspend fun getFundList(
        page: Int
    ): Response<FundListResponse>
}