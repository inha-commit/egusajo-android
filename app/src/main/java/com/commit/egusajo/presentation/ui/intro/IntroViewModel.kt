package com.commit.egusajo.presentation.ui.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.model.BaseState
import com.commit.egusajo.data.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject


sealed class IntroEvents {
    object GoToGallery : IntroEvents()
    object GoToMainActivity : IntroEvents()
    data class ShowSnackMessage(val msg: String) : IntroEvents()
}

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<IntroEvents>()
    val events: SharedFlow<IntroEvents> = _events.asSharedFlow()

    private val _profileImg = MutableStateFlow("")
    val profileImg: StateFlow<String> = _profileImg.asStateFlow()

    fun goToGallery() {
        viewModelScope.launch {
            _events.emit(IntroEvents.GoToGallery)
        }
    }

    fun goToMainActivity() {
        viewModelScope.launch {
            _events.emit(IntroEvents.GoToMainActivity)
        }
    }

    fun imageToUrl(file: MultipartBody.Part) {
        viewModelScope.launch {
            imageRepository.imageToUrl(listOf(file), "users").let{
                when(it){
                    is BaseState.Success -> {
                        _profileImg.value = it.body[0]
                    }
                    is BaseState.Error -> {
                        _events.emit(IntroEvents.ShowSnackMessage(it.msg))
                    }
                }
            }
        }
    }
}