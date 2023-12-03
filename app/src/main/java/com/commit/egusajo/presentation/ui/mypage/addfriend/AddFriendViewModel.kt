package com.commit.egusajo.presentation.ui.mypage.addfriend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.repository.FollowRepository
import com.commit.egusajo.presentation.ui.mypage.addfriend.mapper.toUiFriendData
import com.commit.egusajo.presentation.ui.mypage.friend.MyFriendEvents
import com.commit.egusajo.presentation.ui.mypage.friend.model.UiFriendData
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



data class AddFriendUiState(
    val friendList: List<UiFriendData> = emptyList()
)

sealed class AddFriendEvents{
    data class ShowSnackMessage(val msg: String): AddFriendEvents()
}

@HiltViewModel
class AddFriendViewModel @Inject constructor(
    private val followRepository: FollowRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(AddFriendUiState())
    val uiState: StateFlow<AddFriendUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AddFriendEvents>()
    val events: SharedFlow<AddFriendEvents> = _events.asSharedFlow()

    val keyword = MutableStateFlow("")

    init{
        observeKeyword()
    }

    private fun observeKeyword(){
        keyword.onEach {
            if(it.isNotBlank()){
                followRepository.friendSearch(it).let{ response ->
                    when(response){
                        is BaseState.Success -> {
                            if(response.body.users.isNotEmpty()){
                                _uiState.update { state ->
                                    state.copy(
                                        friendList = response.body.toUiFriendData(::followOrUnFollow)
                                    )
                                }
                            }
                        }
                        is BaseState.Error -> {
                            _events.emit(AddFriendEvents.ShowSnackMessage(response.msg))
                        }
                    }
                }
            } else {
                _uiState.update { state ->
                    state.copy(
                        friendList = emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun followOrUnFollow(isFollowing: Boolean, id: Int) {
        if (isFollowing) {
            viewModelScope.launch {

                followRepository.unFollow(id).let{
                    when(it){
                        is BaseState.Success -> {
                        }

                        is BaseState.Error -> {
                            _events.emit(AddFriendEvents.ShowSnackMessage(it.msg))
                        }
                    }
                }
            }
        } else {
            viewModelScope.launch {
                followRepository.follow(id).let{
                    when(it){
                        is BaseState.Success -> {
                        }

                        is BaseState.Error -> {
                            _events.emit(AddFriendEvents.ShowSnackMessage(it.msg))
                        }
                    }
                }
            }
        }
    }
}