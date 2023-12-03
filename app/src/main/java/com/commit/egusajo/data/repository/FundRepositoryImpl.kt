package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.request.CreateFundRequest
import com.commit.egusajo.data.model.request.ParticipateRequest
import com.commit.egusajo.data.model.response.FundDetailResponse
import com.commit.egusajo.data.model.response.FundListResponse
import com.commit.egusajo.data.model.runRemote
import com.commit.egusajo.data.remote.FundApi
import javax.inject.Inject

class FundRepositoryImpl @Inject constructor(private val api: FundApi) : FundRepository {

    override suspend fun getFundList(page: Int): BaseState<FundListResponse> = runRemote { api.getFundList(page) }
    override suspend fun getFundDetail(presentId: Int): BaseState<FundDetailResponse> = runRemote { api.getFundDetail(presentId) }
    override suspend fun createFund(body: CreateFundRequest): BaseState<Unit> = runRemote { api.createFund(body) }
    override suspend fun participate(fundId: Int, body: ParticipateRequest): BaseState<Unit> = runRemote { api.participate(fundId, body) }
}