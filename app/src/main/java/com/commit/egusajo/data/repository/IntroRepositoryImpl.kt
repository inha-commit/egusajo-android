package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.request.LoginRequest
import com.commit.egusajo.data.model.request.NickCheckRequest
import com.commit.egusajo.data.model.request.SignupRequest
import com.commit.egusajo.data.model.response.LoginResponse
import com.commit.egusajo.data.model.response.NickCheckResponse
import com.commit.egusajo.data.model.runRemote
import com.commit.egusajo.data.remote.IntroApi
import javax.inject.Inject

class IntroRepositoryImpl @Inject constructor(private val api: IntroApi) : IntroRepository {

    override suspend fun login(data: LoginRequest): BaseState<LoginResponse> = runRemote { api.login(data) }
    override suspend fun signup(data: SignupRequest): BaseState<LoginResponse> = runRemote { api.signup(data) }
    override suspend fun checkNick(data: NickCheckRequest): BaseState<NickCheckResponse> = runRemote { api.checkNick(data) }
}