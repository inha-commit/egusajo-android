package com.commit.egusajo.presentation.ui.mypage.fund

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            val response = userRepository.getMyFundList(_uiState.value.page)

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    _uiState.update { state ->
                        state.copy(
                            fundList = _uiState.value.fundList + body.toFundList(::navigateToFundDetail)
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
            _events.emit(MyFundEvents.NavigateToFundDetail(fundId))
        }
    }
}