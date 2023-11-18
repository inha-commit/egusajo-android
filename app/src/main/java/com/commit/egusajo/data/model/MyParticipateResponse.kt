package com.commit.egusajo.data.model

data class MyParticipateResponse(
    val funds: List<ParticipateItem>
)

data class ParticipateItem(
    val fund: ParticipateInfo,
    val present: Present
)

data class ParticipateInfo(
    val id: Int,
    val cost: Int,
    val comment: String,
    val createdAt: String
)




