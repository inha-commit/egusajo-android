package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.FundDetailResponse
import com.commit.egusajo.data.model.FundListResponse
import retrofit2.Response

interface FundRepository {

    suspend fun getFundList(
        page: Int
    ): Response<FundListResponse>

    suspend fun getFundDetail(
        presentId: Int
    ): Response<FundDetailResponse>
}