package com.commit.egusajo.presentation.ui.mypage.participate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.model.BaseState
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
    data class ShowSnackMessage(val msg: String) : MyParticipateEvents()
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
            userRepository.getMyParticipate(_uiState.value.page).let{
                when(it){
                    is BaseState.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                participateList = _uiState.value.participateList + it.body.toUiParticipateDataList(
                                    ::navigateToFundDetail,
                                    if(_uiState.value.participateList.isEmpty()) "" else (_uiState.value.participateList.last().participateDate)
                                ),
                                page = _uiState.value.page + 1
                            )
                        }
                    }
                    is BaseState.Error -> {
                        _events.emit(MyParticipateEvents.ShowSnackMessage(it.msg))
                    }
                }
            }
        }
    }

    private fun navigateToFundDetail(fundId: Int) {
        viewModelScope.launch {
            _events.emit(MyParticipateEvents.NavigateToFundDetail(fundId))
        }
    }

    fun reset(){
        _uiState.update { state ->
            state.copy(
                page = 0,
                hasNext = true
            )
        }
    }

}