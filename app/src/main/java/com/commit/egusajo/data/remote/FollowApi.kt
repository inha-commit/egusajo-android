package com.commit.egusajo.data.remote

import com.commit.egusajo.data.model.Follower
import com.commit.egusajo.data.model.FollowerResponse
import com.commit.egusajo.data.model.Following
import com.commit.egusajo.data.model.FollowingResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FollowApi {

    @GET("/follows/me/followings")
    suspend fun getFollowings(): Response<FollowingResponse>

    @GET("/follows/me/followers")
    suspend fun getFollowers(): Response<FollowerResponse>

    @POST("/follows/{userId}")
    suspend fun follow(
        @Path("userId") userId: Int
    ): Response<Unit>

    @DELETE("/follows/{userId}")
    suspend fun unFollow(
        @Path("userId") userId: Int
    ): Response<Unit>
}