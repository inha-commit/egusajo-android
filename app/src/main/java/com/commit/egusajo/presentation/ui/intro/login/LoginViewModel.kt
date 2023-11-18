package com.commit.egusajo.presentation.ui.intro.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.app.App
import com.commit.egusajo.app.App.Companion.fcmToken
import com.commit.egusajo.data.model.ErrorResponse
import com.commit.egusajo.data.model.LoginRequest
import com.commit.egusajo.data.repository.IntroRepository
import com.commit.egusajo.util.Constants
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class LoginUiState(
    val loginState: LoginState = LoginState.Empty
)

sealed class LoginState {
    object Empty : LoginState()
    object Success : LoginState()
    object NoMember : LoginState()
    data class Error(val msg: String) : LoginState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(private val introRepository: IntroRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun startLogin(
        snsId: String
    ) {
        viewModelScope.launch {

            val response = introRepository.login(
                LoginRequest(
                    fcmId = fcmToken,
                    snsId = snsId
                )
            )

            if (response.isSuccessful) {
                response.body()?.let {
                    App.sharedPreferences.edit()
                        .putString(Constants.X_ACCESS_TOKEN, it.accessToken)
                        .putString(Constants.X_REFRESH_TOKEN, it.refreshToken)
                        .apply()
                }

                _uiState.update { state ->
                    state.copy(
                        loginState = LoginState.Success
                    )
                }
            } else {
                val error =
                    Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                when (error.statusCode) {

                    401 -> _uiState.update { state ->
                        state.copy(
                            loginState = LoginState.Error(error.description)
                        )
                    }

                    400 -> _uiState.update { state ->
                        state.copy(
                            loginState = LoginState.NoMember
                        )
                    }
                }

            }
        }
    }

}