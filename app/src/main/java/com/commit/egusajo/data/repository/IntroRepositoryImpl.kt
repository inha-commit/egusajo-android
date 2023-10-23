package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.LoginRequest
import com.commit.egusajo.data.model.LoginResponse
import com.commit.egusajo.data.remote.IntroApi
import retrofit2.Response
import javax.inject.Inject

class IntroRepositoryImpl @Inject constructor(private val api: IntroApi) : IntroRepository {

    override suspend fun login(data: LoginRequest): Response<LoginResponse> = api.login(data)

}