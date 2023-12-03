package com.commit.egusajo.data.model.response

data class FundDetailResponse(
    val fundings: List<FundingItem>?,
    val present: Present,
    val presentImages: List<String>,
    val user: User,
    val participateStatus: String
)

data class FundingItem(
    val funding: FundingInfo,
    val user: Sender
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