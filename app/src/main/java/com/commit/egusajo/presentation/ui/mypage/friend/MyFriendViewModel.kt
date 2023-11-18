package com.commit.egusajo.presentation.ui.mypage.friend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.repository.FollowRepository
import com.commit.egusajo.presentation.ui.mypage.friend.mapper.toUiFriendData
import com.commit.egusajo.presentation.ui.mypage.friend.model.UiFriendData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyFriendUiState(
    val friendList: List<UiFriendData> = emptyList(),
    val followerState: Boolean = true,
    val followingState: Boolean = false
)

@HiltViewModel
class MyFriendViewModel @Inject constructor(
    private val followRepository: FollowRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyFriendUiState())
    val uiState: StateFlow<MyFriendUiState> = _uiState.asStateFlow()

    fun getFollowerList() {
        viewModelScope.launch {
            val response = followRepository.getFollowers()

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    _uiState.update { state ->
                        state.copy(
                            friendList = body.toUiFriendData(::followOrUnFollow),
                            followerState = true,
                            followingState = false
                        )
                    }
                }
            } else {

            }
        }
    }

    fun getFollowingList() {
        viewModelScope.launch {
            val response = followRepository.getFollowings()

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    _uiState.update { state ->
                        state.copy(
                            friendList = body.toUiFriendData(::followOrUnFollow),
                            followerState = false,
                            followingState = true
                        )
                    }
                }
            } else {

            }
        }
    }

    private fun followOrUnFollow(isFollowing: Boolean, id: Int) {
        if (isFollowing) {
            viewModelScope.launch {
                val response = followRepository.unFollow(id)
                if (response.isSuccessful) {
                    if (_uiState.value.followerState) {
                        getFollowerList()
                    } else {
                        getFollowingList()
                    }
                } else {

                }
            }
        } else {
            viewModelScope.launch {
                val response = followRepository.follow(id)
                if (response.isSuccessful) {
                    if (_uiState.value.followerState) {
                        getFollowerList()
                    } else {
                        getFollowingList()
                    }
                } else {

                }
            }
        }
    }


}