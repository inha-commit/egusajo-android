package com.commit.egusajo.presentation.ui.global.detail.model

data class UiFundDetailData(
    val presentImages: List<String> = emptyList(),
    val dDay: String = "",
    val name: String = "",
    val presentName: String = "",
    val presentLink: String = "",
    val presentDescription: String = "",
    val count: String = "",
    val goal: String = "",
    val money: String = "",
    val percent: String = "",
    val fundList: List<ParticipateData> = emptyList()
)

data class ParticipateData(
    val userId: Int,
    val participateId: Int,
    val profileImg: String,
    val cost: String,
    val comment: String
)
