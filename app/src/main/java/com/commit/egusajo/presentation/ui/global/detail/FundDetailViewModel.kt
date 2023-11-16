package com.commit.egusajo.presentation.ui.global.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.repository.FundRepository
import com.commit.egusajo.presentation.ui.global.detail.mapper.toUiFundDetailData
import com.commit.egusajo.presentation.ui.global.detail.model.UiFundDetailData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FundDetailUiState(
    val fundDetail: UiFundDetailData = UiFundDetailData()
)

@HiltViewModel
class FundDetailViewModel @Inject constructor(
    private val fundRepository: FundRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FundDetailUiState())
    val uiState: StateFlow<FundDetailUiState> = _uiState.asStateFlow()

    fun getFundDetail(fundId: Int){
        viewModelScope.launch {
            val response = fundRepository.getFundDetail(fundId)

            if(response.isSuccessful){
                response.body()?.let{ body ->
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

}