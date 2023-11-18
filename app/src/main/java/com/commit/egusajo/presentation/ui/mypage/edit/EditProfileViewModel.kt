package com.commit.egusajo.presentation.ui.mypage.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.model.MyInfoResponse
import com.commit.egusajo.data.repository.UserRepository
import com.commit.egusajo.presentation.ui.bindProfileImg
import com.commit.egusajo.presentation.ui.mypage.edit.mapper.toUiEditProfileData
import com.commit.egusajo.presentation.ui.mypage.edit.model.UiEditProfileData
import com.commit.egusajo.presentation.ui.mypage.main.model.UiMyPageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditProfileUiState(
    val isDataChange: Boolean = false
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    private var originProfile = ""
    private var originNick = ""
    private var originName = ""
    private var originBirth = ""
    private var originAlarm = false

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

    private fun observeName() {
        name.onEach {
            if (it != originName) {
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

}