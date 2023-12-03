package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.BaseState
import okhttp3.MultipartBody

interface ImageRepository {

    suspend fun imageToUrl(
        data: List<MultipartBody.Part>,
        type: String
    ): BaseState<List<String>>
}