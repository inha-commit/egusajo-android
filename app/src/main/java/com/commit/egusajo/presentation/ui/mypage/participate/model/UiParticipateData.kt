package com.commit.egusajo.presentation.ui.mypage.participate.model

data class UiParticipateData(
    val fundId: Int,
    val deadLine: String,
    val title: String,
    val presentName: String,
    val goal: Int,
    val date: String,
    val participateInfo: String,
    val presentImgUrl: String,
    val onItemClickListener: (Int) -> Unit
)
