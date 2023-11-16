package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.CreateFundRequest
import com.commit.egusajo.data.model.FundDetailResponse
import com.commit.egusajo.data.model.FundListResponse
import com.commit.egusajo.data.remote.FundApi
import retrofit2.Response
import javax.inject.Inject

class FundRepositoryImpl @Inject constructor(private val api: FundApi) : FundRepository {

    override suspend fun getFundList(page: Int): Response<FundListResponse> = api.getFundList(page)
    override suspend fun getFundDetail(presentId: Int): Response<FundDetailResponse> = api.getFundDetail(presentId)
    override suspend fun createFund(body: CreateFundRequest): Response<Unit> = api.createFund(body)
}