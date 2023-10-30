package com.commit.egusajo.data.remote

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ImageApi {

    @Multipart
    @POST("/images")
    suspend fun imageToUrl(
        @Part images: List<MultipartBody.Part>,
        @Query("type") type: String
    ): Response<List<String>>
}