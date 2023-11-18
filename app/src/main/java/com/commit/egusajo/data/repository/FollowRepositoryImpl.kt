package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.Follower
import com.commit.egusajo.data.model.FollowerResponse
import com.commit.egusajo.data.model.Following
import com.commit.egusajo.data.model.FollowingResponse
import com.commit.egusajo.data.remote.FollowApi
import retrofit2.Response
import javax.inject.Inject

class FollowRepositoryImpl @Inject constructor(
    private val api: FollowApi
): FollowRepository{

    override suspend fun getFollowers(): Response<FollowerResponse> = api.getFollowers()
    override suspend fun getFollowings(): Response<FollowingResponse> = api.getFollowings()
    override suspend fun follow(userId: Int): Response<Unit> = api.follow(userId)
    override suspend fun unFollow(userId: Int): Response<Unit> = api.unFollow(userId)

}