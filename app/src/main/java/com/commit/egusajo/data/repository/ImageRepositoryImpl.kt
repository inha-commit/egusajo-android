package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.runRemote
import com.commit.egusajo.data.remote.ImageApi
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(private val api: ImageApi) : ImageRepository {

    override suspend fun imageToUrl(
        data: List<MultipartBody.Part>,
        type: String
    ): BaseState<List<String>> = runRemote { api.imageToUrl(data, type) }
}