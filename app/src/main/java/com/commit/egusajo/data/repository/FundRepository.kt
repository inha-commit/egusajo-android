package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.CreateFundRequest
import com.commit.egusajo.data.model.FundDetailResponse
import com.commit.egusajo.data.model.FundListResponse
import com.commit.egusajo.data.model.ParticipateRequest
import retrofit2.Response

interface FundRepository {

    suspend fun getFundList(
        page: Int
    ): Response<FundListResponse>

    suspend fun getFundDetail(
        presentId: Int
    ): Response<FundDetailResponse>

    suspend fun createFund(
        body: CreateFundRequest
    ): Response<Unit>

    suspend fun participate(
        fundId: Int,
        body: ParticipateRequest
    ): Response<Unit>
}