package com.commit.egusajo.presentation.ui.global.detail.mapper

import com.commit.egusajo.data.model.FundDetailResponse
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
        count = (this.fundList?.size?:0).toString() + "명",
        goal = this.present.goal,
        money = this.present.money,
        percent = ((this.present.money / this.present.goal) * 100).toString() + "%",
        fundList = this.fundList?.map { item ->
            ParticipateData(
                userId = item.sender.id,
                participateId = item.funding.id,
                profileImg = item.sender.profileImgSrc,
                cost = item.funding.cost,
                comment = item.funding.comment
            )
        }
    )
}