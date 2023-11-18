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
    val originUserData: UiEditProfileData = UiEditProfileData(),
    val isDataChange: Boolean = false
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    val profileImg = MutableStateFlow("")
    val nickName = MutableStateFlow("")
    val name = MutableStateFlow("")
    val birthDay = MutableStateFlow("")
    val alarm = MutableStateFlow(false)

    private fun observeNick(){
        nickName.onEach {
        }.launchIn(viewModelScope)
    }

    fun getOriginInfo(){
        viewModelScope.launch {
            val response = userRepository.getMyInfo()

            if(response.isSuccessful){
                response.body()?.let{ body ->
                    nickName.value = body.nickname
                    name.value = body.name
                    birthDay.value = body.birthday
                    alarm.value = body.alarm
                    profileImg.value  = body.profileImgSrc

                    _uiState.update { state ->
                        state.copy(
                            originUserData = body.toUiEditProfileData()
                        )
                    }
                }
            } else {

            }
        }
    }

}