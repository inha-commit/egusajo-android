package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.response.FollowerResponse
import com.commit.egusajo.data.model.response.FollowingResponse

interface FollowRepository {
    suspend fun getFollowings(): BaseState<FollowingResponse>

    suspend fun getFollowers(): BaseState<FollowerResponse>

    suspend fun follow(userId: Int): BaseState<Unit>

    suspend fun unFollow(userId: Int): BaseState<Unit>

}