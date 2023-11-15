package com.commit.egusajo.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.repository.HomeRepository
import com.commit.egusajo.presentation.ui.home.mapper.toFundList
import com.commit.egusajo.presentation.ui.home.model.Fund
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeUiState(
    val fundList: List<Fund> = emptyList(),
    val page: Int = 0,
    val hasNext: Boolean = true
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun getFundList(){
        viewModelScope.launch {

            val response = homeRepository.getFundList(_uiState.value.page)

            if(response.isSuccessful){
                response.body()?.let{ body ->
                    _uiState.update { state ->
                        state.copy(
                            fundList = _uiState.value.fundList + body.toFundList()
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

}