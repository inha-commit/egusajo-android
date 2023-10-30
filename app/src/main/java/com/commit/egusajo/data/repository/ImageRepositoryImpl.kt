package com.commit.egusajo.data.repository

import com.commit.egusajo.data.remote.ImageApi
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(private val api: ImageApi) : ImageRepository {

    override suspend fun imageToUrl(
        data: List<MultipartBody.Part>,
        type: String
    ): Response<List<String>> = api.imageToUrl(data, type)
}