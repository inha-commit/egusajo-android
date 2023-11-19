package com.commit.egusajo.presentation

sealed class InputState {
    object Empty : InputState()
    data class Success(val msg: String) : InputState()
    data class Error(val msg: String) : InputState()
}

sealed class LoadingState {
    object Empty : LoadingState()
    data class IsLoading(val state: Boolean) : LoadingState()
}
