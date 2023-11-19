package com.commit.egusajo.presentation.ui.global.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.model.ParticipateRequest
import com.commit.egusajo.data.repository.FundRepository
import com.commit.egusajo.presentation.ui.MainEvent
import com.commit.egusajo.presentation.ui.global.payment.mapper.toUiPaymentData
import com.commit.egusajo.presentation.ui.global.payment.model.UiPaymentData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
            val response = fundRepository.getFundDetail(fundId)

            if(response.isSuccessful){
                response.body()?.let{
                    _uiState.update { state ->
                        state.copy(
                            fundDetail = it.toUiPaymentData()
                        )
                    }
                }
            } else {

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
            val response = fundRepository.participate(
                fundId = fundId,
                body = ParticipateRequest(
                    cost = price.value.toInt(),
                    comment = comment.value
                )
            )

            if(response.isSuccessful){
                delay(500)
                _events.emit(FundPaymentEvents.NavigateBack)
            } else {

            }
        }
    }

    fun setFundId(id: Int){
        fundId = id
    }

}