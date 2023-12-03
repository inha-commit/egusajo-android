package com.commit.egusajo.data.remote

import com.commit.egusajo.data.model.response.FollowerResponse
import com.commit.egusajo.data.model.response.FollowingResponse
import com.commit.egusajo.data.model.response.FriendSearchResponse
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

    @GET("/users/nickname/{nickname}")
    suspend fun searchUser(
        @Path("nickname") nickname: String
    ): Response<FriendSearchResponse>
}