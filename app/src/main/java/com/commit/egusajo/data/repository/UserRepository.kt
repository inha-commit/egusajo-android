package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.request.PatchMyInfoRequest
import com.commit.egusajo.data.model.response.FundListResponse
import com.commit.egusajo.data.model.response.MyInfoResponse
import com.commit.egusajo.data.model.response.MyParticipateResponse

interface UserRepository {

    suspend fun getMyInfo(): BaseState<MyInfoResponse>
    suspend fun patchMyInfo(
        body: PatchMyInfoRequest
    ): BaseState<Unit>

    suspend fun withdrawal(): BaseState<Unit>

    suspend fun getMyFundList(
        page: Int
    ): BaseState<FundListResponse>

    suspend fun getMyParticipate(
        page: Int
    ): BaseState<MyParticipateResponse>
}