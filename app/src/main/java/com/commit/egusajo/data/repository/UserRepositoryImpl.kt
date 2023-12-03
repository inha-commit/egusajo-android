package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.request.PatchMyInfoRequest
import com.commit.egusajo.data.model.response.FundListResponse
import com.commit.egusajo.data.model.response.MyInfoResponse
import com.commit.egusajo.data.model.response.MyParticipateResponse
import com.commit.egusajo.data.model.runRemote
import com.commit.egusajo.data.remote.UserApi
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserApi
): UserRepository {

    override suspend fun getMyInfo(): BaseState<MyInfoResponse> = runRemote { api.getMyInfo() }
    override suspend fun patchMyInfo(body: PatchMyInfoRequest): BaseState<Unit> = runRemote { api.patchMyInfo(body) }
    override suspend fun withdrawal(): BaseState<Unit> = runRemote { api.withdrawal() }
    override suspend fun getMyFundList(page: Int): BaseState<FundListResponse> = runRemote { api.getMyFund(page) }
    override suspend fun getMyParticipate(page: Int): BaseState<MyParticipateResponse> = runRemote { api.getMyParticipate(page) }
}