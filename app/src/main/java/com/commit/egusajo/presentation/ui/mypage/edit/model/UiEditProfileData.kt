package com.commit.egusajo.presentation.ui.mypage.edit.model

data class UiEditProfileData(
    val name: String = "",
    val nickName: String = "",
    val birthday: String = "",
    val profileImgSrc: String = "",
    val fcmId: String = "",
    val alarm: Boolean = false
)
