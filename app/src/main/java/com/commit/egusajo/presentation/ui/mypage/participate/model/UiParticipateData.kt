package com.commit.egusajo.presentation.ui.mypage.participate.model

data class UiParticipateData(
    val fundId: Int = 0,
    val deadLine: String = "",
    val title: String = "",
    val presentName: String = "",
    val goal: Int = 0,
    val date: String = "",
    val cost: Int = 0,
    val participateDate: String = "",
    val presentImgUrl: String = "",
    val onItemClickListener: (Int) -> Unit,
    val viewType: String = "",
)
