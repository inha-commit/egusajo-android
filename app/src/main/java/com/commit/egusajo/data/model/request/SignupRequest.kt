package com.commit.egusajo.data.model.request

data class SignupRequest(
    val snsId: String,
    val nickname: String,
    val name: String,
    val birthday: String,
    val fcmId: String = "",
    val account: String = "",
    val bank: String = "",
    val profileImgSrc: String? = null
)
