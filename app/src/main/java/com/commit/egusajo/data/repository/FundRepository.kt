package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.request.CreateFundRequest
import com.commit.egusajo.data.model.request.ParticipateRequest
import com.commit.egusajo.data.model.response.FundDetailResponse
import com.commit.egusajo.data.model.response.FundListResponse

interface FundRepository {

    suspend fun getFundList(
        page: Int
    ): BaseState<FundListResponse>

    suspend fun getFundDetail(
        presentId: Int
    ): BaseState<FundDetailResponse>

    suspend fun createFund(
        body: CreateFundRequest
    ): BaseState<Unit>

    suspend fun participate(
        fundId: Int,
        body: ParticipateRequest
    ): BaseState<Unit>
}