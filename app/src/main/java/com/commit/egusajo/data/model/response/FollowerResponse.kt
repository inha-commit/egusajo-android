package com.commit.egusajo.data.model.response

data class FollowerResponse(
    val followers: List<Follower>
)



data class Follower(
    val birthday: String,
    val id: Int,
    val name: String,
    val nickname: String,
    val profileImgSrc: String,
    val isFollowing: Boolean
)