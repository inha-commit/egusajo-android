package com.commit.egusajo.data.model

data class SignupRequest(
    val snsId: String,
    val nickname: String,
    val name: String,
    val birthday: String,
    val fcmId: String = "",
    val profileImageSrc: String? = null
)
