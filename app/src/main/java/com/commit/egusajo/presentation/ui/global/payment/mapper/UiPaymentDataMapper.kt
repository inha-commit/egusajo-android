package com.commit.egusajo.presentation.ui.global.payment.mapper

import com.commit.egusajo.data.model.response.FundDetailResponse
import com.commit.egusajo.presentation.ui.global.payment.model.UiPaymentData

internal fun FundDetailResponse.toUiPaymentData(): UiPaymentData {
    return UiPaymentData(
        name = this.user.name,
        presentName = this.present.name,
        goal = this.present.goal,
        presentId = this.present.id.toString(),
        shortComment = this.present.shortComment,
        productImg = this.present.representImage
    )
}