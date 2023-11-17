package com.commit.egusajo.data.model

data class CreateFundRequest(
    val name: String,
    val productLink: String,
    val goal: Int,
    val deadline: String,
    val presentImages: List<String>,
    val representImage: String,
    val shortComment: String="",
    val longComment: String
)
