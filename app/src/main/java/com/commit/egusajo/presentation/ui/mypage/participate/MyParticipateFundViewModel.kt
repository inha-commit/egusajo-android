package com.commit.egusajo.presentation.ui.mypage.participate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.repository.UserRepository
import com.commit.egusajo.presentation.ui.home.mapper.toFundList
import com.commit.egusajo.presentation.ui.mypage.fund.MyFundEvents
import com.commit.egusajo.presentation.ui.mypage.participate.mapper.toUiParticipateDataList
import com.commit.egusajo.presentation.ui.mypage.participate.model.UiParticipateData
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

data class MyParticipateUiState(
    val participateList: List<UiParticipateData> = emptyList(),
    val page: Int = 0,
    val hasNext: Boolean = true,
)

sealed class MyParticipateEvents {
    data class NavigateToFundDetail(val fundId: Int) : MyParticipateEvents()
}


@HiltViewModel
class MyParticipateFundViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyParticipateUiState())
    val uiState: StateFlow<MyParticipateUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<MyParticipateEvents>()
    val events: SharedFlow<MyParticipateEvents> = _events.asSharedFlow()

    fun getMyParticipateList() {
        viewModelScope.launch {
            val response = userRepository.getMyParticipate(_uiState.value.page)

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    _uiState.update { state ->
                        state.copy(
                            participateList = _uiState.value.participateList + body.toUiParticipateDataList(
                                ::navigateToFundDetail
                            )
                        )
                    }
                }
            } else {

            }

            _uiState.update { state ->
                state.copy(
                    page = _uiState.value.page + 1
                )
            }
        }
    }

    private fun navigateToFundDetail(fundId: Int) {
        viewModelScope.launch {
            _events.emit(MyParticipateEvents.NavigateToFundDetail(fundId))
        }
    }

}