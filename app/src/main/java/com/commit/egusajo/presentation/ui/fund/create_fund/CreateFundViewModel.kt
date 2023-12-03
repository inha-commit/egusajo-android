package com.commit.egusajo.presentation.ui.fund.create_fund

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.model.request.CreateFundRequest
import com.commit.egusajo.data.repository.FundRepository
import com.commit.egusajo.presentation.InputState
import com.commit.egusajo.util.Validation
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CreateFundUiState(
    val productLinkState: InputState = InputState.Empty,
    val deadLineState: InputState = InputState.Empty
)

sealed class CreateFundEvent{
    object GoToGallery: CreateFundEvent()
    object NavigateToBack : CreateFundEvent()
    data class ShowToastMessage(val msg: String) : CreateFundEvent()
    data class ShowSnackMessage(val msg: String) : CreateFundEvent()
}

@HiltViewModel
class CreateFundViewModel @Inject constructor(
    private val fundRepository: FundRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateFundUiState())
    val uiState: StateFlow<CreateFundUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<CreateFundEvent>()
    val events: SharedFlow<CreateFundEvent> = _events.asSharedFlow()

    val productName = MutableStateFlow("")
    val goal = MutableStateFlow("")
    val deadLine = MutableStateFlow("")
    val productLink = MutableStateFlow("")
    val longComment = MutableStateFlow("")
    val representImage = MutableStateFlow("")
    val presentImages = MutableStateFlow(listOf<String>())

    init {
        observeProductLink()
        observeGoal()
        observeDeadLine()
    }

    private fun observeProductLink() {
        productLink.onEach {
            if (it.isBlank()) {
                _uiState.update { state ->
                    state.copy(
                        productLinkState = InputState.Empty
                    )
                }
            } else {
                if (Validation.validateLink(it)) {
                    _uiState.update { state ->
                        state.copy(
                            productLinkState = InputState.Success("올바른 link 입니다")
                        )
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            productLinkState = InputState.Error("https:// 형식에 맞는 링크를 삽입하세요")
                        )
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun observeGoal() {
        goal.onEach {
            Validation.validateMoney(it)
        }.launchIn(viewModelScope)
    }

    private fun observeDeadLine() {
        deadLine.onEach {
            if (it.isBlank()) {
                _uiState.update { state ->
                    state.copy(
                        deadLineState = InputState.Empty
                    )
                }
            } else {
                if (Validation.validateDate(it)) {
                    _uiState.update { state ->
                        state.copy(
                            deadLineState = InputState.Success("올바른 날짜입니다")
                        )
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            deadLineState = InputState.Error("올바른 날짜를 입력하세요")
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    val isDataReady = combine(
        productName,
        goal,
        deadLine,
        productLink,
        longComment
    ) { productName, goal, deadLine, productLink, longComment->
        productName.isNotBlank() && goal.isNotBlank() && deadLine.isNotBlank() && productLink.isNotBlank() && longComment.isNotBlank()
                && _uiState.value.deadLineState is InputState.Success && _uiState.value.productLinkState is InputState.Success
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        false
    )

    fun setDeadline(data: String) {
        deadLine.value = data
    }

    fun setImages(data: List<String>){
        representImage.value = data[0]
        presentImages.value = data
    }

    fun createFund(){
        viewModelScope.launch {
            fundRepository.createFund(
                CreateFundRequest(
                    name = productName.value,
                    productLink = productLink.value,
                    goal = goal.value.toInt(),
                    deadline = deadLine.value,
                    representImage = representImage.value,
                    presentImages = presentImages.value,
                    longComment = longComment.value
                )
            ).let{
                when(it){
                    is BaseState.Success -> {
                        _events.emit(CreateFundEvent.ShowToastMessage("펀딩 생성 성공!"))
                        _events.emit(CreateFundEvent.NavigateToBack)
                    }
                    is BaseState.Error -> _events.emit(CreateFundEvent.ShowSnackMessage(it.msg))
                }
            }
        }
    }

    fun goToGallery(){
        viewModelScope.launch {
            _events.emit(CreateFundEvent.GoToGallery)
        }
    }

}