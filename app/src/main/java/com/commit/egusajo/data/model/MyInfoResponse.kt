package com.commit.egusajo.data.model

data class MyInfoResponse(
    val name: String,
    val nickname: String,
    val birthday: String,
    val profileImgSrc: String,
    val fcmId: String,
    val alarm: Boolean
)
