package com.commit.egusajo.presentation.ui.global.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.request.ParticipateRequest
import com.commit.egusajo.data.repository.FundRepository
import com.commit.egusajo.presentation.ui.global.payment.mapper.toUiPaymentData
import com.commit.egusajo.presentation.ui.global.payment.model.UiPaymentData
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


data class FundPaymentUiState(
    val fundDetail: UiPaymentData = UiPaymentData()
)

sealed class FundPaymentEvents{
    data class GoToBootPay(
        val data: UiPaymentData,
        val price: Int
    ): FundPaymentEvents()

    object NavigateBack: FundPaymentEvents()
    data class ShowSnackMessage(val msg: String) : FundPaymentEvents()
    data class ShowToastMessage(val msg: String) : FundPaymentEvents()
}


@HiltViewModel
class FundPaymentViewModel @Inject constructor(
    private val fundRepository: FundRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(FundPaymentUiState())
    val uiState: StateFlow<FundPaymentUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<FundPaymentEvents>()
    val events: SharedFlow<FundPaymentEvents> = _events.asSharedFlow()

    private var fundId = -1

    val price = MutableStateFlow("")
    val comment = MutableStateFlow("")

    fun getFundDetail(){
        viewModelScope.launch{
            fundRepository.getFundDetail(fundId).let{
                when(it){
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

    fun goToBootPay(){
        viewModelScope.launch {
            _events.emit(FundPaymentEvents.GoToBootPay(
                _uiState.value.fundDetail, price.value.toInt()
            ))
        }
    }

    fun participate(){
        viewModelScope.launch {
           fundRepository.participate(
                fundId = fundId,
                body = ParticipateRequest(
                    cost = price.value.toInt(),
                    comment = comment.value
                )
            ).let{
                when(it){
                    is BaseState.Success -> {
                        _events.emit(FundPaymentEvents.ShowToastMessage("${price}원 참여 성공"))
                        _events.emit(FundPaymentEvents.NavigateBack)
                    }
                    is BaseState.Error -> {
                        _events.emit(FundPaymentEvents.ShowSnackMessage(it.msg))
                    }
                }
           }
        }
    }

    fun setFundId(id: Int){
        fundId = id
    }

}