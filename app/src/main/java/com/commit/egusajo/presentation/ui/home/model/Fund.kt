package com.commit.egusajo.presentation.ui.home.model

data class Fund(
    val fundId: Int,
    val dDay: String,
    val title: String,
    val productTitle: String,
    val productPrice: String,
    val date: String,
    val productImgUrl: String?="",
    val onItemClickListener: (Int) -> Unit
)