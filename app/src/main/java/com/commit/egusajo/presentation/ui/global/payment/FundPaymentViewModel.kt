package com.commit.egusajo.presentation.ui.global.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.request.ParticipateRequest
import com.commit.egusajo.data.repository.FundRepository
import com.commit.egusajo.presentation.InputState
import com.commit.egusajo.presentation.ui.global.payment.mapper.toUiPaymentData
import com.commit.egusajo.presentation.ui.global.payment.model.UiPaymentData
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


data class FundPaymentUiState(
    val fundDetail: UiPaymentData = UiPaymentData(),
    val priceState: InputState = InputState.Empty,
    val payEnable: Boolean = false
)

sealed class FundPaymentEvents {
    data class GoToBootPay(
        val data: UiPaymentData,
        val price: Int
    ) : FundPaymentEvents()

    object NavigateBack : FundPaymentEvents()
    data class ShowSnackMessage(val msg: String) : FundPaymentEvents()
    data class ShowToastMessage(val msg: String) : FundPaymentEvents()
    object ShowLoading : FundPaymentEvents()
    object DismissLoading : FundPaymentEvents()
}


@HiltViewModel
class FundPaymentViewModel @Inject constructor(
    private val fundRepository: FundRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FundPaymentUiState())
    val uiState: StateFlow<FundPaymentUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<FundPaymentEvents>()
    val events: SharedFlow<FundPaymentEvents> = _events.asSharedFlow()

    private var fundId = -1

    val price = MutableStateFlow("")
    val comment = MutableStateFlow("")

    init {
        observePrice()
    }

    private fun observePrice() {
        price.onEach {
            if (it.isNotBlank()){
                val amount = it.toInt()
                if (amount < 1000) {
                    _uiState.update { state ->
                        state.copy(
                            priceState = InputState.Error("1000원 이상만 펀딩 가능해요!"),
                            payEnable = false
                        )
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            priceState = InputState.Success("가능한 금액이에요"),
                            payEnable = true
                        )
                    }
                }
            } else {
                _uiState.update { state ->
                    state.copy(
                        priceState = InputState.Empty,
                        payEnable = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getFundDetail() {
        viewModelScope.launch {
            fundRepository.getFundDetail(fundId).let {
                when (it) {
                    is BaseState.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                fundDetail = it.body.toUiPaymentData()
                            )
                        }
                    }

                    is BaseState.Error -> {
                        _events.emit(FundPaymentEvents.ShowSnackMessage(it.msg))
                    }
                }
            }
        }
    }

    fun goToBootPay() {
        viewModelScope.launch {
            _events.emit(
                FundPaymentEvents.GoToBootPay(
                    _uiState.value.fundDetail, price.value.toInt()
                )
            )
        }
    }

    fun participate() {
        viewModelScope.launch {
            _events.emit(FundPaymentEvents.ShowLoading)
            fundRepository.participate(
                fundId = fundId,
                body = ParticipateRequest(
                    cost = price.value.toInt(),
                    comment = comment.value
                )
            ).let {
                _events.emit(FundPaymentEvents.DismissLoading)
                when (it) {
                    is BaseState.Success -> {
                        _events.emit(FundPaymentEvents.ShowToastMessage("${price.value}원 펀딩 성공"))
                        _events.emit(FundPaymentEvents.NavigateBack)
                    }

                    is BaseState.Error -> {
                        _events.emit(FundPaymentEvents.ShowSnackMessage(it.msg))
                    }
                }
            }
        }
    }

    fun setFundId(id: Int) {
        fundId = id
    }

}