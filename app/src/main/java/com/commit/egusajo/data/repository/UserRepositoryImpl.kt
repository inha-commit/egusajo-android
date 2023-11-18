package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.FundListResponse
import com.commit.egusajo.data.model.MyInfoResponse
import com.commit.egusajo.data.model.MyParticipateResponse
import com.commit.egusajo.data.model.PatchMyInfoRequest
import com.commit.egusajo.data.remote.UserApi
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserApi
): UserRepository {

    override suspend fun getMyInfo(): Response<MyInfoResponse> = api.getMyInfo()
    override suspend fun patchMyInfo(body: PatchMyInfoRequest): Response<Unit> = api.patchMyInfo(body)
    override suspend fun withdrawal(): Response<Unit> = api.withdrawal()
    override suspend fun getMyFundList(page: Int): Response<FundListResponse> = api.getMyFund(page)
    override suspend fun getMyParticipate(page: Int): Response<MyParticipateResponse> = api.getMyParticipate(page)
}