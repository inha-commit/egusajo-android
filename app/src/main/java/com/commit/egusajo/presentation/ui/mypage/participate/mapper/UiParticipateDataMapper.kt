package com.commit.egusajo.presentation.ui.mypage.participate.mapper


import com.commit.egusajo.data.model.response.MyParticipateResponse
import com.commit.egusajo.presentation.ui.mypage.participate.model.UiParticipateData


fun MyParticipateResponse.toUiParticipateDataList(onItemClickListener: (Int) -> Unit, lastDate: String): List<UiParticipateData>{
    val result = mutableListOf<UiParticipateData>()
    val newData = this.funds.map {
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
            onItemClickListener = onItemClickListener,
            viewType = "DATA"
        )
    }.toMutableList()

    var curDate = lastDate
    newData.forEach {
        if(curDate != it.participateDate){
            curDate = it.participateDate
            result.add(UiParticipateData(
                participateDate = curDate,
                viewType = "DATE",
                onItemClickListener = onItemClickListener
            ))
            result.add(it)
        } else {
            result.add(it)
        }
    }
    return result
}