package com.commit.egusajo.presentation.ui.mypage.main.mapper

import com.commit.egusajo.data.model.MyInfoResponse
import com.commit.egusajo.presentation.ui.mypage.main.model.UiMyPageData


fun MyInfoResponse.toUiMyPageData(): UiMyPageData{
    return UiMyPageData(
        profileImg = profileImgSrc,
        nickName = nickname,
        name = name
    )
}