package com.commit.egusajo.data.model

data class PatchMyInfoRequest(
    val name: String,
    val nickname: String,
    val birthday: String,
    val profileImgSrc: String,
    val alarm: Boolean
)
