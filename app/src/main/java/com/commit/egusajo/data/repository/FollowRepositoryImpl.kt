package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.response.FollowerResponse
import com.commit.egusajo.data.model.response.FollowingResponse
import com.commit.egusajo.data.model.runRemote
import com.commit.egusajo.data.remote.FollowApi
import retrofit2.Response
import javax.inject.Inject

class FollowRepositoryImpl @Inject constructor(
    private val api: FollowApi
): FollowRepository{

    override suspend fun getFollowers(): BaseState<FollowerResponse> = runRemote { api.getFollowers() }
    override suspend fun getFollowings(): BaseState<FollowingResponse> = runRemote { api.getFollowings() }
    override suspend fun follow(userId: Int): BaseState<Unit> = runRemote { api.follow(userId) }
    override suspend fun unFollow(userId: Int): BaseState<Unit> = runRemote { api.unFollow(userId) }

}