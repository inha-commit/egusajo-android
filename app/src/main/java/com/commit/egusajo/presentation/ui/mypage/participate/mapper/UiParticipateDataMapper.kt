package com.commit.egusajo.presentation.ui.mypage.participate.mapper


import com.commit.egusajo.data.model.MyParticipateResponse
import com.commit.egusajo.presentation.ui.mypage.participate.model.UiParticipateData


fun MyParticipateResponse.toUiParticipateDataList(onItemClickListener: (Int) -> Unit): List<UiParticipateData>{
    return this.funds.map {
        UiParticipateData(
            fundId = it.present.id,
            deadLine = it.present.deadline + " 🔥",
            presentName = it.present.name,
            presentImgUrl = it.present.representImage,
            goal = it.present.goal,
            date = it.present.createdAt,
            title = it.present.shortComment,
            participateDate = it.fund.createdAt,
            cost = it.fund.cost,
            onItemClickListener = onItemClickListener
        )
    }
}