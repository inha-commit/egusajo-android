package com.commit.egusajo.presentation.ui.mypage.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.model.ErrorResponse
import com.commit.egusajo.data.model.NickCheckRequest
import com.commit.egusajo.data.model.PatchMyInfoRequest
import com.commit.egusajo.data.repository.IntroRepository
import com.commit.egusajo.data.repository.UserRepository
import com.commit.egusajo.presentation.InputState
import com.commit.egusajo.util.Validation
import com.google.gson.Gson
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

data class EditProfileUiState(
    val isDataChange: Boolean = false,
    val nickState: InputState = InputState.Empty,
    val birthState: InputState = InputState.Empty
)

sealed class EditProfileEvents {
    object NavigateToBack : EditProfileEvents()
}

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val introRepository: IntroRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<EditProfileEvents>()
    val events: SharedFlow<EditProfileEvents> = _events.asSharedFlow()

    private var originProfile = ""
    private var originNick = ""
    private var originName = ""
    private var originBirth = ""
    private var originAlarm = false
    private var originFcmId = ""

    val profileImg = MutableStateFlow("")
    val nickName = MutableStateFlow("")
    val name = MutableStateFlow("")
    val birthDay = MutableStateFlow("")
    val alarm = MutableStateFlow(false)

    init {
        observeNick()
        observeBirth()
        observeName()
        observeAlarm()
    }

    private fun observeNick() {
        nickName.onEach {
            if (it != originNick) {
                if (it.isBlank()) {
                    _uiState.update { state ->
                        state.copy(
                            nickState = InputState.Error("바꿀 닉네임을 입력하세요"),
                            isDataChange = false
                        )
                    }
                } else {
                    val response = introRepository.checkNick(NickCheckRequest(it))
                    if (response.isSuccessful) {
                        _uiState.update { state ->
                            state.copy(
                                nickState = InputState.Success("사용 가능한 닉네임 입니다"),
                                isDataChange = true
                            )
                        }
                    } else {
                        val error =
                            Gson().fromJson(
                                response.errorBody()?.string(),
                                ErrorResponse::class.java
                            )
                        when (error.code) {
                            1100, 1101 -> {
                                _uiState.update { state ->
                                    state.copy(
                                        nickState = InputState.Error(error.description),
                                        isDataChange = false
                                    )
                                }
                            }

                            else -> {
                                _uiState.update { state ->
                                    state.copy(
                                        nickState = InputState.Error("닉네임 검사 실패"),
                                        isDataChange = false
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                _uiState.update { state ->
                    state.copy(
                        nickState = InputState.Empty,
                        isDataChange = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun observeName() {
        name.onEach {
            if (it != originName && it.isNotBlank()) {
                _uiState.update { state ->
                    state.copy(
                        isDataChange = true
                    )
                }
            } else {
                _uiState.update { state ->
                    state.copy(
                        isDataChange = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun observeBirth() {
        birthDay.onEach {

            if (it != originBirth) {
                if (it.isBlank()) {
                    _uiState.update { state ->
                        state.copy(
                            birthState = InputState.Empty,
                            isDataChange = true
                        )
                    }
                } else {
                    if (Validation.validateDate(it)) {
                        _uiState.update { state ->
                            state.copy(
                                birthState = InputState.Success("올바른 날짜입니다"),
                                isDataChange = true
                            )
                        }
                    } else {
                        _uiState.update { state ->
                            state.copy(
                                birthState = InputState.Error("올바른 날짜를 입력하세요"),
                                isDataChange = true
                            )
                        }
                    }
                }
            } else {
                _uiState.update { state ->
                    state.copy(
                        birthState = InputState.Empty,
                        isDataChange = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun observeAlarm() {
        alarm.onEach {
            if (it != originAlarm) {
                _uiState.update { state ->
                    state.copy(
                        isDataChange = true
                    )
                }
            } else {
                _uiState.update { state ->
                    state.copy(
                        isDataChange = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    fun getOriginInfo() {
        viewModelScope.launch {
            val response = userRepository.getMyInfo()

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    originNick = body.nickname
                    originName = body.name
                    originBirth = body.birthday
                    originAlarm = body.alarm
                    originProfile = body.profileImgSrc
                    originFcmId = body.fcmId
                    nickName.value = body.nickname
                    name.value = body.name
                    birthDay.value = body.birthday
                    alarm.value = body.alarm
                    profileImg.value = body.profileImgSrc
                }
            } else {

            }
        }
    }

    fun setProfileImg(url: String) {
        profileImg.value = url
    }

    fun setBirth(data: String) {
        birthDay.value = data
    }

    fun patchProfile() {
        viewModelScope.launch {
            val response = userRepository.patchMyInfo(
                PatchMyInfoRequest(
                    name = name.value,
                    nickname = nickName.value,
                    profileImgSrc = profileImg.value,
                    birthday = birthDay.value,
                    fcmId = originFcmId,
                    alarm = alarm.value
                )
            )

            if (response.isSuccessful) {
                _events.emit(EditProfileEvents.NavigateToBack)
            } else {

            }
        }
    }


}