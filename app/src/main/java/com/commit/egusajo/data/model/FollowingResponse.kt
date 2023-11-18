package com.commit.egusajo.data.model

data class FollowingResponse(
    val followings: List<Following>
)

data class Following(
    val birthday: String,
    val id: Int,
    val name: String,
    val nickname: String,
    val profileImgSrc: String
)