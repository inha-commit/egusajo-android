package com.commit.egusajo.presentation.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

sealed class MainEvent{
}

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private val _events =  MutableSharedFlow<MainEvent>()
    val events:SharedFlow<MainEvent> = _events.asSharedFlow()

}