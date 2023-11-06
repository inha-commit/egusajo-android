package com.commit.egusajo.data.model

data class FundListResponse(
    val presentItems: List<PresentItem>
)

data class PresentItem(
    val present: Present,
    val user: User
)

data class Present(
    val complete: Boolean,
    val deadline: String,
    val goal: Int,
    val id: Int,
    val longComment: String,
    val money: Int,
    val nickname: String,
    val profileImgSrc: String,
    val representImage: String,
    val shortComment: String
)

data class User(
    val id: Int,
    val name: String,
    val nickname: String,
    val profileImgSrc: String
)