package com.commit.egusajo.presentation.ui.intro.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.app.App.Companion.fcmToken
import com.commit.egusajo.app.App.Companion.sharedPreferences
import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.request.NickCheckRequest
import com.commit.egusajo.data.model.request.SignupRequest
import com.commit.egusajo.data.repository.IntroRepository
import com.commit.egusajo.presentation.InputState
import com.commit.egusajo.presentation.ui.intro.SnsId
import com.commit.egusajo.util.Constants.X_ACCESS_TOKEN
import com.commit.egusajo.util.Constants.X_REFRESH_TOKEN
import com.commit.egusajo.util.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignupUiState(
    val isDataChange: Boolean = false,
    val nickState: InputState = InputState.Empty,
    val birthState: InputState = InputState.Empty,
    val isDataReady: Boolean = false
)


sealed class SignupEvents {
   object NavigateToMainActivity: SignupEvents()
    data class ShowToastMessage(val msg: String): SignupEvents()
    data class ShowSnackMessage(val msg: String): SignupEvents()
    object ShowLoading: SignupEvents()
    object DismissLoading: SignupEvents()
}

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val introRepository: IntroRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<SignupEvents>()
    val events: SharedFlow<SignupEvents> = _events.asSharedFlow()

    val profileImg = MutableStateFlow("")
    val nickName = MutableStateFlow("")
    val name = MutableStateFlow("")
    val birthDay = MutableStateFlow("")
    private var bank = ""
    private var account = ""

    init {
        observeNick()
        observeBirth()
    }

    private fun observeNick() {
        nickName.onEach {
            if (it.isBlank()) {
                _uiState.update { state ->
                    state.copy(
                        nickState = InputState.Error("닉네임을 입력하세요"),
                        isDataReady = false
                    )
                }
            } else {
                introRepository.checkNick(NickCheckRequest(it)).let{ response ->

                    when(response){
                        is BaseState.Success -> {
                            _uiState.update { state ->
                                state.copy(
                                    nickState = InputState.Success("사용 가능한 닉네임 입니다"),
                                    isDataReady = true
                                )
                            }
                        }
                        is BaseState.Error -> {
                            when (response.code) {
                                1100, 1101 -> {
                                    _uiState.update { state ->
                                        state.copy(
                                            nickState = InputState.Error(response.msg),
                                            isDataReady = false
                                        )
                                    }
                                }

                                else -> {
                                    _uiState.update { state ->
                                        state.copy(
                                            nickState = InputState.Error("닉네임 검사 실패"),
                                            isDataReady = false
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }.launchIn(viewModelScope)
    }

    private fun observeBirth() {
        birthDay.onEach {

            if (it.isBlank()) {
                _uiState.update { state ->
                    state.copy(
                        birthState = InputState.Empty,
                        isDataReady =  false
                    )
                }
            } else {
                if (Validation.validateDate(it)) {
                    _uiState.update { state ->
                        state.copy(
                            birthState = InputState.Success("올바른 날짜입니다"),
                            isDataReady =  true
                        )
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            birthState = InputState.Error("올바른 날짜를 입력하세요"),
                            isDataReady = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun signup() {

        viewModelScope.launch {
            _events.emit(SignupEvents.ShowLoading)
            introRepository.signup(
                SignupRequest(
                    snsId = SnsId.snsId,
                    nickname = nickName.value,
                    name = name.value,
                    bank = bank,
                    fcmId = fcmToken,
                    account = account,
                    birthday = birthDay.value,
                    profileImgSrc = profileImg.value.ifBlank { null })
            ).let{
                _events.emit(SignupEvents.DismissLoading)
                when(it){
                    is BaseState.Success -> {
                        sharedPreferences.edit()
                            .putString(X_ACCESS_TOKEN, it.body.accessToken)
                            .putString(X_REFRESH_TOKEN, it.body.refreshToken)
                            .apply()
                        _events.emit(SignupEvents.ShowToastMessage("회원가입 성공!"))
                        _events.emit(
                            SignupEvents.NavigateToMainActivity
                        )
                    }

                    is BaseState.Error -> {
                        when (it.code) {
                            1001, 1101 -> {
                                _events.emit(
                                    SignupEvents.ShowToastMessage(it.msg)
                                )
                            }

                            else -> {
                                _events.emit(
                                    SignupEvents.ShowSnackMessage(it.msg)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun setProfileImg(url: String) {
        profileImg.value = url
    }

    fun setBirth(data: String){
        birthDay.value = data
    }

    fun setAccountInfo(accountData: String, bankData: String){
        account = accountData
        bank = bankData
    }
}