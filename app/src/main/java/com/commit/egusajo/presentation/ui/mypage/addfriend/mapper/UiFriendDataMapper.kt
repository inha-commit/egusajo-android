package com.commit.egusajo.presentation.ui.mypage.addfriend.mapper

import com.commit.egusajo.data.model.response.FriendSearchResponse
import com.commit.egusajo.presentation.ui.mypage.friend.model.UiFriendData


fun FriendSearchResponse.toUiFriendData(followOrUnFollow: (Boolean, Int) -> Unit): List<UiFriendData> {
    return this.users.map {
        UiFriendData(
            id = it.id,
            nick = it.nickname,
            profileImg = it.profileImgSrc,
            isFollowing = it.isFollowing,
            followOrUnfollow = followOrUnFollow
        )
    }
}