package com.commit.egusajo.data.model

import com.google.gson.Gson
import retrofit2.Response

sealed class BaseState<out T> {
    data class Success<out T>(val body: T) : BaseState<T>()
    data class Error(val msg: String, val code: Int) : BaseState<Nothing>()
}

suspend fun <T> runRemote(block: suspend () -> Response<T>): BaseState<T> {
    return try {
        val response = block()
        if (response.isSuccessful) {
            response.body()?.let {
                BaseState.Success(it)
            } ?: run {
                BaseState.Error("응답이 비어있습니다", 0)
            }
        } else {
            val error = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
            BaseState.Error(error.description, error.code)
        }
    } catch (e: Exception) {
        BaseState.Error("네트워크 통신 에러", 0)
    }
}

data class ErrorResponse(
    val statusCode: Int,
    val message: String,
    val description: String,
    val code: Int
)
