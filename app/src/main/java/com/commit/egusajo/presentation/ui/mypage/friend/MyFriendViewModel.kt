package com.commit.egusajo.presentation.ui.mypage.friend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.repository.FollowRepository
import com.commit.egusajo.presentation.LoadingState
import com.commit.egusajo.presentation.ui.mypage.friend.mapper.toUiFriendData
import com.commit.egusajo.presentation.ui.mypage.friend.model.UiFriendData
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

data class MyFriendUiState(
    val friendList: List<UiFriendData> = emptyList(),
    val followerState: Boolean = true,
    val followingState: Boolean = false,
    val loading: LoadingState = LoadingState.Empty
)

sealed class MyFriendEvents{
    data class ShowSnackMessage(val msg: String): MyFriendEvents()
}

@HiltViewModel
class MyFriendViewModel @Inject constructor(
    private val followRepository: FollowRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyFriendUiState())
    val uiState: StateFlow<MyFriendUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<MyFriendEvents>()
    val events: SharedFlow<MyFriendEvents> = _events.asSharedFlow()

    fun getFollowerList() {
        viewModelScope.launch {

            _uiState.update { state ->
                state.copy(
                    loading = LoadingState.IsLoading(true)
                )
            }

            followRepository.getFollowers().let{
                when(it){
                    is BaseState.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                friendList = it.body.toUiFriendData(::followOrUnFollow),
                                followerState = true,
                                followingState = false,
                                loading = LoadingState.IsLoading(false)
                            )
                        }
                    }

                    is BaseState.Error -> {
                        _events.emit(MyFriendEvents.ShowSnackMessage(it.msg))
                    }
                }
            }
        }
    }

    fun getFollowingList() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    loading = LoadingState.IsLoading(true)
                )
            }

            followRepository.getFollowings().let{
                when(it){
                    is BaseState.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                friendList = it.body.toUiFriendData(::followOrUnFollow),
                                followerState = false,
                                followingState = true,
                                loading = LoadingState.IsLoading(false)
                            )
                        }
                    }

                    is BaseState.Error -> {
                        _events.emit(MyFriendEvents.ShowSnackMessage(it.msg))
                    }
                }
            }
        }
    }

    private fun followOrUnFollow(isFollowing: Boolean, id: Int) {
        if (isFollowing) {
            viewModelScope.launch {
                followRepository.unFollow(id).let{
                    when(it){
                        is BaseState.Success -> {
                            if (_uiState.value.followerState) {
                                getFollowerList()
                            } else {
                                getFollowingList()
                            }
                        }

                        is BaseState.Error -> {
                            _events.emit(MyFriendEvents.ShowSnackMessage(it.msg))
                        }
                    }
                }
            }
        } else {
            viewModelScope.launch {
                followRepository.follow(id).let{
                    when(it){
                        is BaseState.Success -> {
                            if (_uiState.value.followerState) {
                                getFollowerList()
                            } else {
                                getFollowingList()
                            }
                        }

                        is BaseState.Error -> {
                            _events.emit(MyFriendEvents.ShowSnackMessage(it.msg))
                        }
                    }
                }
            }
        }
    }


}