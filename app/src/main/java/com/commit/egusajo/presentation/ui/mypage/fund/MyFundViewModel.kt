package com.commit.egusajo.presentation.ui.mypage.fund

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.repository.UserRepository
import com.commit.egusajo.presentation.ui.home.mapper.toFundList
import com.commit.egusajo.presentation.ui.home.model.Fund
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


data class MyFundUiState(
    val fundList: List<Fund> = emptyList(),
    val page: Int = 0,
    val hasNext: Boolean = true
)

sealed class MyFundEvents {
    data class NavigateToFundDetail(val fundId: Int) : MyFundEvents()
    data class ShowSnackMessage(val msg: String) : MyFundEvents()
    data class ShowToastMessage(val msg: String) : MyFundEvents()
}

@HiltViewModel
class MyFundViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyFundUiState())
    val uiState: StateFlow<MyFundUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<MyFundEvents>()
    val events: SharedFlow<MyFundEvents> = _events.asSharedFlow()

    fun getMyFundList() {
        viewModelScope.launch {
            userRepository.getMyFundList(_uiState.value.page).let{

                when(it){
                    is BaseState.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                fundList = _uiState.value.fundList + it.body.toFundList(::navigateToFundDetail),
                                page = _uiState.value.page + 1
                            )
                        }
                    }

                    is BaseState.Error -> {
                        _events.emit(MyFundEvents.ShowSnackMessage(it.msg))
                    }
                }

            }
        }
    }


    private fun navigateToFundDetail(fundId: Int) {
        viewModelScope.launch {
            _events.emit(MyFundEvents.NavigateToFundDetail(fundId))
        }
    }
}