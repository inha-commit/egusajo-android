package com.commit.egusajo.presentation.ui.intro.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AccountVerificationUiState(
    val bankList: List<String> = emptyList()
)

sealed class AccountVerificationEvents {
    data class NavigateToSignup(val bank: String, val account: String) : AccountVerificationEvents()
}

@HiltViewModel
class AccountVerificationViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AccountVerificationUiState())
    val uiState: StateFlow<AccountVerificationUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AccountVerificationEvents>()
    val events: SharedFlow<AccountVerificationEvents> = _events.asSharedFlow()

    val account = MutableStateFlow("")
    private val bank = MutableStateFlow("")

    val isDataReady = combine(account, bank) { account, bank ->
        account.isNotBlank() && bank.isNotBlank()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), false
    )

    fun checkAccount() {

        viewModelScope.launch {
            // todo 계좌검증 API 호출

            // todo 성공시 Signup 으로 이동
            _events.emit(AccountVerificationEvents.NavigateToSignup(bank.value, account.value))
        }

    }

    fun getBankList() {

        _uiState.update { state ->
            state.copy(
                bankList = listOf("하나은행", "국민은행", "IBK은행", "카카오뱅크", " 토스뱅크", "부산은행", "경남은행")
            )
        }
    }

    fun setBank(data: String){
        bank.value = data
    }

}