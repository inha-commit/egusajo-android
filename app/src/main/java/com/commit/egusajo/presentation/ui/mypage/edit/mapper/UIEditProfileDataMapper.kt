package com.commit.egusajo.presentation.ui.mypage.edit.mapper

import com.commit.egusajo.data.model.response.MyInfoResponse
import com.commit.egusajo.presentation.ui.mypage.edit.model.UiEditProfileData


fun MyInfoResponse.toUiEditProfileData(): UiEditProfileData{
    return UiEditProfileData(
         name = this.name,
        nickName = this.nickname,
        birthday = this.birthday,
        profileImgSrc = this.profileImgSrc,
        fcmId = this.fcmId,
        alarm = this.alarm
    )
}
