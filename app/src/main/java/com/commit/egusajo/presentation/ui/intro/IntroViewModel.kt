package com.commit.egusajo.presentation.ui.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class IntroEvents{
    object GoToGallery: IntroEvents()
    object GoToMainActivity: IntroEvents()
}

@HiltViewModel
class IntroViewModel @Inject constructor(): ViewModel() {

    private val _events = MutableSharedFlow<IntroEvents>()
    val events: SharedFlow<IntroEvents> = _events.asSharedFlow()

    fun goToGallery(){
        viewModelScope.launch {
            _events.emit(IntroEvents.GoToGallery)
        }
    }

    fun goToMainActivity(){
        viewModelScope.launch{
            _events.emit(IntroEvents.GoToMainActivity)
        }
    }

}