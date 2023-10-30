package com.commit.egusajo.data.repository

import okhttp3.MultipartBody
import retrofit2.Response

interface ImageRepository {

    suspend fun imageToUrl(
        data: List<MultipartBody.Part>,
        type: String
    ): Response<List<String>>
}