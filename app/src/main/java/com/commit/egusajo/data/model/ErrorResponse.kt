package com.commit.egusajo.data.model

data class ErrorResponse(
    val statusCode: Int,
    val message: String,
    val error: String
)
