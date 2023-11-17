package com.commit.egusajo.data.model

data class PatchMyInfoRequest(
    val name: String,
    val nickname: String,
    val birthday: String,
    val profileImgSsrc: String,
    val fcmId: String,
    val alarm: Boolean
)
