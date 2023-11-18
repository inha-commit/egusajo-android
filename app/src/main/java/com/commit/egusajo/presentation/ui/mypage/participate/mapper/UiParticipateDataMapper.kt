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
            goal = it.present.goal.toString() + " 원",
            date ="",
            participateInfo = "${it.fund.cost} 원   ${it.fund.createdAt}",
            onItemClickListener = onItemClickListener
        )
    }
}