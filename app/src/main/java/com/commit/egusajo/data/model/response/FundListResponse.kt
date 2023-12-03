package com.commit.egusajo.data.model.response

data class FundListResponse(
    val presents: List<PresentItem>
)

data class PresentItem(
    val user: User,
    val present: Present,
)

data class Present(
    val id: Int,
    val name: String,
    val productLink: Any?="",
    val complete: Boolean,
    val goal: Int=0,
    val money: Int,
    val deadline: String,
    val shortComment: String,
    val representImage: String,
    val longComment: String,
    val createdAt: String
)

data class User(
    val id: Int,
    val name: String,
    val nickname: String,
    val profileImgSrc: String?=""
)