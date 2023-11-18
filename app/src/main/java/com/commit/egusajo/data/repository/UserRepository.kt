package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.FundListResponse
import com.commit.egusajo.data.model.MyInfoResponse
import com.commit.egusajo.data.model.MyParticipateResponse
import com.commit.egusajo.data.model.PatchMyInfoRequest
import retrofit2.Response

interface UserRepository {

    suspend fun getMyInfo(): Response<MyInfoResponse>
    suspend fun patchMyInfo(
        body: PatchMyInfoRequest
    ): Response<Unit>

    suspend fun withdrawal(): Response<Unit>

    suspend fun getMyFundList(
        page: Int
    ): Response<FundListResponse>

    suspend fun getMyParticipate(
        page: Int
    ): Response<MyParticipateResponse>
}