package com.commit.egusajo.presentation.ui.mypage.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.repository.UserRepository
import com.commit.egusajo.presentation.ui.mypage.main.mapper.toUiMyPageData
import com.commit.egusajo.presentation.ui.mypage.main.model.UiMyPageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class MyPageUiState(
    val myInfo: UiMyPageData = UiMyPageData()
)

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    fun getMyInfo(){
        viewModelScope.launch {
            val response = userRepository.getMyInfo()

            if(response.isSuccessful){
                response.body()?.let{
                    _uiState.update { state ->
                        state.copy(
                            myInfo = it.toUiMyPageData()
                        )
                    }
                }
            } else {

            }
        }
    }


}