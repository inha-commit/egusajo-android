package com.commit.egusajo.data.repository

import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.request.LoginRequest
import com.commit.egusajo.data.model.request.NickCheckRequest
import com.commit.egusajo.data.model.request.SignupRequest
import com.commit.egusajo.data.model.response.LoginResponse
import com.commit.egusajo.data.model.response.NickCheckResponse

interface IntroRepository {

    suspend fun login(
        data: LoginRequest
    ): BaseState<LoginResponse>

    suspend fun signup(
        data: SignupRequest
    ): BaseState<LoginResponse>

    suspend fun checkNick(
        data: NickCheckRequest
    ): BaseState<NickCheckResponse>

}