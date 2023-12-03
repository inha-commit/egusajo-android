package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.response.FollowerResponse
import com.commit.egusajo.data.model.response.FollowingResponse
import com.commit.egusajo.data.model.response.FriendSearchResponse

interface FollowRepository {
    suspend fun getFollowings(): BaseState<FollowingResponse>

    suspend fun getFollowers(): BaseState<FollowerResponse>

    suspend fun follow(userId: Int): BaseState<Unit>

    suspend fun unFollow(userId: Int): BaseState<Unit>

    suspend fun friendSearch(nickname: String): BaseState<FriendSearchResponse>

}