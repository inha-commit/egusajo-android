package com.commit.egusajo.presentation.ui.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<IntroEvents>()
    val events: SharedFlow<IntroEvents> = _events.asSharedFlow()

    private val _profileImg = MutableStateFlow("")
    val profileImg: StateFlow<String> = _profileImg.asStateFlow()

    private val _fcmToken = MutableStateFlow("")
    val fcmToken: StateFlow<String> = _fcmToken.asStateFlow()

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
            val response = imageRepository.imageToUrl(listOf(file), "users")

            if (response.isSuccessful) {
                response.body()?.let {
                    _profileImg.value = it[0]
                }
            }
        }
    }

    fun setFcm(fcm: String){
        _fcmToken.value = fcm
    }

}