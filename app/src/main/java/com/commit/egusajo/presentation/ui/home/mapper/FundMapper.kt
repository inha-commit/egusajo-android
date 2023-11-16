package com.commit.egusajo.presentation.ui.home.mapper

import com.commit.egusajo.data.model.FundListResponse
import com.commit.egusajo.presentation.ui.home.model.Fund


fun FundListResponse.toFundList(onItemClickListener: (Int) -> Unit): List<Fund>{
    return this.presents.map {
        Fund(
            fundId = it.present.id,
            title = "'${it.user.name}'님의 생일입니다",
            productTitle = it.present.name,
            productImgUrl = it.user.profileImgSrc,
            productPrice = it.present.goal,
            dDay = it.present.deadline + " 🔥",
            date = "2020년 7월 10일",
            onItemClickListener = onItemClickListener
        )
    }
}