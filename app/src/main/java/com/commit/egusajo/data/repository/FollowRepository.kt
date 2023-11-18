package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.Follower
import com.commit.egusajo.data.model.FollowerResponse
import com.commit.egusajo.data.model.Following
import com.commit.egusajo.data.model.FollowingResponse
import retrofit2.Response

interface FollowRepository {
    suspend fun getFollowings(): Response<FollowingResponse>

    suspend fun getFollowers(): Response<FollowerResponse>

    suspend fun follow(userId: Int): Response<Unit>

    suspend fun unFollow(userId: Int): Response<Unit>

}