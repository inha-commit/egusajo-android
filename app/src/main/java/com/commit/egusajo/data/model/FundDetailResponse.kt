package com.commit.egusajo.data.model

data class FundDetailResponse(
    val fundList: List<FundingItem>,
    val present: Present,
    val presentImages: List<String>,
    val user: User
)

data class FundingItem(
    val funding: FundingInfo,
    val sender: Sender
)

data class FundingInfo(
    val comment: String,
    val cost: Int,
    val id: Int
)

data class Sender(
    val id: Int,
    val name: String,
    val nickname: String,
    val profileImgSrc: String
)