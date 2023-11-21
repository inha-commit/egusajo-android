package com.commit.egusajo.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.repository.FundRepository
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


data class HomeUiState(
    val fundList: List<Fund> = emptyList(),
    val page: Int = 0,
    val hasNext: Boolean = true
)

sealed class HomeEvents{
    data class NavigateToFundDetail(val fundId: Int): HomeEvents()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fundRepository: FundRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<HomeEvents>()
    val events: SharedFlow<HomeEvents> = _events.asSharedFlow()

    fun getFundList(){
        viewModelScope.launch {
            if(_uiState.value.hasNext){
                val response = fundRepository.getFundList(_uiState.value.page)

                if(response.isSuccessful){
                    response.body()?.let{ body ->
                        if(body.presents.isEmpty()){
                            _uiState.update { state ->
                                state.copy(
                                    hasNext = false
                                )
                            }
                        } else {
                            _uiState.update { state ->
                                state.copy(
                                    page = _uiState.value.page + 1,
                                    fundList = _uiState.value.fundList + body.toFundList(::navigateToFundDetail)
                                )
                            }
                        }
                    }
                } else {

                }
            }
        }
    }

    private fun navigateToFundDetail(fundId: Int){
        viewModelScope.launch {
            _events.emit(HomeEvents.NavigateToFundDetail(fundId))
        }
    }

    fun reset(){
        _uiState.update { state ->
            state.copy(
                fundList = emptyList(),
                hasNext = true,
                page = 0
            )
        }
    }


}