package com.commit.egusajo.presentation.ui.global.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.repository.FundRepository
import com.commit.egusajo.presentation.ui.global.detail.mapper.toUiFundDetailData
import com.commit.egusajo.presentation.ui.global.detail.model.UiFundDetailData
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

data class FundDetailUiState(
    val fundDetail: UiFundDetailData = UiFundDetailData()
)

sealed class FundDetailEvents {
    data class NavigateToFundPayment(val fundId: Int) : FundDetailEvents()
}

@HiltViewModel
class FundDetailViewModel @Inject constructor(
    private val fundRepository: FundRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FundDetailUiState())
    val uiState: StateFlow<FundDetailUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<FundDetailEvents>()
    val events: SharedFlow<FundDetailEvents> = _events.asSharedFlow()

    private var fundId = -1

    fun getFundDetail(fundId: Int) {
        viewModelScope.launch {
            val response = fundRepository.getFundDetail(fundId)

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    _uiState.update { state ->
                        state.copy(
                            fundDetail = body.toUiFundDetailData()
                        )
                    }
                }
            } else {

            }
        }
    }

    fun setFundId(id: Int) {
        fundId = id
    }

    fun navigateToFundPayment() {
        viewModelScope.launch {
            _events.emit(FundDetailEvents.NavigateToFundPayment(fundId))
        }
    }

}