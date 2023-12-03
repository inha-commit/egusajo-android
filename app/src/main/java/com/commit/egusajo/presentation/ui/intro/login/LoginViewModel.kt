package com.commit.egusajo.presentation.ui.intro.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.app.App
import com.commit.egusajo.app.App.Companion.fcmToken
import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.request.LoginRequest
import com.commit.egusajo.data.repository.IntroRepository
import com.commit.egusajo.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

sealed class LoginEvent{
    data class ShowSnackMessage(val msg: String) : LoginEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(private val introRepository: IntroRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<LoginEvent>()
    val events: SharedFlow<LoginEvent> = _events.asSharedFlow()

    fun startLogin(
        snsId: String
    ) {
        viewModelScope.launch {

            introRepository.login(
                LoginRequest(
                    fcmId = fcmToken,
                    snsId = snsId
                )
            ).let{
                when(it){
                    is BaseState.Success -> {
                        App.sharedPreferences.edit()
                            .putString(Constants.X_ACCESS_TOKEN, it.body.accessToken)
                            .putString(Constants.X_REFRESH_TOKEN, it.body.refreshToken)
                            .apply()

                        _uiState.update { state ->
                            state.copy(
                                loginState = LoginState.Success
                            )
                        }
                    }

                    is BaseState.Error -> {
                        when(it.code){
                            1002 -> _uiState.update { state ->
                                state.copy(
                                    loginState = LoginState.NoMember
                                )
                            }
                            else -> _uiState.update { state ->
                                state.copy(
                                    loginState = LoginState.Error(it.msg)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}