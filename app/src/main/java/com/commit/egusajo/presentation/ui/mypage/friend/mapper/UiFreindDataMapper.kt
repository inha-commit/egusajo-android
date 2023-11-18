package com.commit.egusajo.presentation.ui.mypage.friend.mapper

import com.commit.egusajo.data.model.FollowerResponse
import com.commit.egusajo.data.model.FollowingResponse
import com.commit.egusajo.presentation.ui.mypage.friend.model.UiFriendData


fun FollowerResponse.toUiFriendData(followOrUnFollow: (Boolean, Int) -> Unit): List<UiFriendData> {
    return this.followers.map {
        UiFriendData(
            id = it.id,
            nick = it.nickname,
            profileImg = it.profileImgSrc,
            isFollowing = it.isFollowing,
            followOrUnfollow = followOrUnFollow
        )
    }
}

fun FollowingResponse.toUiFriendData(followOrUnFollow: (Boolean, Int) -> Unit): List<UiFriendData> {
    return this.followings.map {
        UiFriendData(
            id = it.id,
            nick = it.nickname,
            profileImg = it.profileImgSrc,
            isFollowing = true,
            followOrUnfollow = followOrUnFollow
        )
    }
}