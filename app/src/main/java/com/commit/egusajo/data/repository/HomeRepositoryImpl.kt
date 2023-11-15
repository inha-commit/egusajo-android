package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.FundListResponse
import com.commit.egusajo.data.remote.HomeApi
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val api: HomeApi) : HomeRepository {

    override suspend fun getFundList(page: Int): Response<FundListResponse>  = api.getFundList(page)
}