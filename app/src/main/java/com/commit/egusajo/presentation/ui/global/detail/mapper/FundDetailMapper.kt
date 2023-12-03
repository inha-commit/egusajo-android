package com.commit.egusajo.presentation.ui.global.detail.mapper

import com.commit.egusajo.data.model.response.FundDetailResponse
import com.commit.egusajo.presentation.ui.global.detail.model.ParticipateData
import com.commit.egusajo.presentation.ui.global.detail.model.UiFundDetailData


internal fun FundDetailResponse.toUiFundDetailData(): UiFundDetailData {
    return UiFundDetailData(
        presentImages = this.presentImages,
        dDay = this.present.deadline + " 🔥",
        name = this.user.name,
        presentName = this.present.name,
        presentLink = this.present.productLink.toString(),
        presentDescription = this.present.longComment,
        count = (this.fundings?.size?:0).toString() + "명",
        goal = this.present.goal,
        money = this.present.money,
        percent = "(%.2f%%)".format((this.present.money.toDouble() / this.present.goal.toDouble()) * 100),
        fundList = this.fundings?.let{ it.map { item ->
            ParticipateData(
                userId = item.user.id,
                participateId = item.funding.id,
                profileImg = item.user.profileImgSrc,
                cost = item.funding.cost,
                comment = item.funding.comment,
                name = item.user.name
            )
        }}
    )
}