package com.commit.egusajo.presentation.ui.global.detail.model

data class UiFundDetailData(
    val presentImages: List<String> = emptyList(),
    val dDay: String = "",
    val name: String = "",
    val presentName: String = "",
    val presentLink: String = "",
    val presentDescription: String = "",
    val count: String = "",
    val goal: Int = 0,
    val money: Int = 0,
    val percent: String = "",
    val fundList: List<ParticipateData>? = emptyList(),
    val participateStatus: String = ""
)

data class ParticipateData(
    val userId: Int,
    val participateId: Int,
    val profileImg: String,
    val name: String,
    val cost: Int,
    val comment: String
)
