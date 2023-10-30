package com.commit.egusajo.presentation.ui.intro.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.app.App.Companion.sharedPreferences
import com.commit.egusajo.data.model.ErrorResponse
import com.commit.egusajo.data.model.NickCheckRequest
import com.commit.egusajo.data.model.SignupRequest
import com.commit.egusajo.data.repository.IntroRepository
import com.commit.egusajo.presentation.ui.intro.SnsId
import com.commit.egusajo.util.Constants.TAG
import com.commit.egusajo.util.Constants.X_ACCESS_TOKEN
import com.commit.egusajo.util.Constants.X_REFRESH_TOKEN
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignupUiState(
    val nickState: SignupState = SignupState.Empty,
    val signUp: SignupState = SignupState.Empty
)

sealed class SignupState {
    object Empty : SignupState()
    object Success : SignupState()
    object Failure : SignupState()
    data class Error(val msg: String) : SignupState()
}

@HiltViewModel
class SignupViewModel @Inject constructor(private val introRepository: IntroRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    val name = MutableStateFlow("")
    val nick = MutableStateFlow("")
    val birth = MutableStateFlow("")
    val profileUrl = MutableStateFlow("")

    init {
        checkNick()
    }

    val isDataReady = combine(name, nick, birth) { name, nick, birth ->
        name.isNotBlank()
                && nick.isNotBlank()
                && birth.isNotBlank()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        false
    )

    private fun checkNick() {
        nick.onEach {
            Log.d(TAG, it)
            val response = introRepository.checkNick(NickCheckRequest(it))

            if (response.isSuccessful) {
                _uiState.update { state ->
                    state.copy(
                        nickState = SignupState.Success
                    )
                }
            } else {
                val error =
                    Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                when (error.code) {
                    1100, 1101 -> {
                        _uiState.update { state ->
                            state.copy(
                                nickState = SignupState.Error(error.description)
                            )
                        }
                    }

                    else -> {
                        _uiState.update { state ->
                            state.copy(
                                nickState = SignupState.Error("닉네임 검사 실패")
                            )
                        }
                    }
                }

            }
        }.launchIn(viewModelScope)
    }

    fun signup() {

        viewModelScope.launch {
            val response =
                introRepository.signup(
                    SignupRequest(
                        snsId = SnsId.snsId,
                        nickname = nick.value,
                        birthday = birth.value
                    )
                )

            if (response.isSuccessful) {

                response.body()?.let{
                    sharedPreferences.edit()
                        .putString(X_ACCESS_TOKEN, "Bearer " + it.accessToken)
                        .putString(X_REFRESH_TOKEN, it.refreshToken)
                        .apply()
                }

                _uiState.update { state ->
                    state.copy(
                        signUp = SignupState.Success
                    )
                }

            } else {
                val error =
                    Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                when (error.code) {
                    1001, 1101 -> {
                        _uiState.update { state ->
                            state.copy(
                                signUp = SignupState.Error(error.description)
                            )
                        }
                    }

                    else -> {
                        _uiState.update { state ->
                            state.copy(
                                signUp = SignupState.Error("네트워크 오류")
                            )
                        }
                    }
                }

            }

        }
    }

    fun setProfileImg(url: String) {
        profileUrl.value = url
    }

}