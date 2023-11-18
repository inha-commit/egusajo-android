package com.commit.egusajo.presentation.ui.mypage.friend.model

data class UiFriendData(
    val id: Int,
    val nick: String,
    val profileImg: String,
    val isFollowing: Boolean,
    val followOrUnfollow: (Boolean, Int) -> Unit
)
