package com.commit.egusajo.presentation.ui.fund.create_fund

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.util.Validation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CreateFundViewModel @Inject constructor(): ViewModel() {


    val productName = MutableStateFlow("")
    val goal = MutableStateFlow("")
    val deadLine = MutableStateFlow("")
    val productLink = MutableStateFlow("")
    val longComment = MutableStateFlow("")
    val presentImages = MutableStateFlow(emptyList<String>())

    init {
        observeProductLink()
        observeGoal()
        observeDeadLine()
    }

    private fun observeProductLink(){
        productLink.onEach {
            Validation.validateLink(it)
        }.launchIn(viewModelScope)
    }

    private fun observeGoal(){
        goal.onEach {
            Validation.validateMoney(it)
        }.launchIn(viewModelScope)
    }

    private fun observeDeadLine(){
        deadLine.onEach {
            Validation.validateDate(it)
        }.launchIn(viewModelScope)
    }

    val isDataReady = combine(productName, goal, deadLine, productLink, longComment){
        productName, goal, deadLine, productLink, longComment->
        productName.isNotBlank() && goal.isNotBlank() && deadLine.isNotBlank() && productLink.isNotBlank() && longComment.isNotBlank()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        false
    )

    fun setDeadline(data: String){
        deadLine.value = data
    }

}