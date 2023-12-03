package com.commit.egusajo.presentation.ui.global.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.repository.FundRepository
import com.commit.egusajo.presentation.ui.global.detail.mapper.toUiFundDetailData
import com.commit.egusajo.presentation.ui.global.detail.model.UiFundDetailData
import com.google.gson.Gson
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
    data class GoToProductLink(val link: String) : FundDetailEvents()
    data class ShowSnackMessage(val msg: String) : FundDetailEvents()
    data class ShowToastMessage(val msg: String) : FundDetailEvents()
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
            fundRepository.getFundDetail(fundId).let{
                when(it){
                    is BaseState.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                fundDetail = it.body.toUiFundDetailData()
                            )
                        }
                    }
                    is BaseState.Error -> {
                        _events.emit(FundDetailEvents.ShowSnackMessage(it.msg))
                    }
                }
            }
        }
    }

    fun setFundId(id: Int) {
        fundId = id
    }

    fun goToProductLink(){
        viewModelScope.launch {
            _events.emit(FundDetailEvents.GoToProductLink(_uiState.value.fundDetail.presentLink))
        }
    }

    fun navigateToFundPayment() {
        viewModelScope.launch {
            _events.emit(FundDetailEvents.NavigateToFundPayment(fundId))
        }
    }

}