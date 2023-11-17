package com.commit.egusajo.presentation.ui.main

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

sealed class MainEvent {
    object GoToGallery : MainEvent()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<MainEvent>()
    val events: SharedFlow<MainEvent> = _events.asSharedFlow()

    private val _image = MutableStateFlow(listOf<String>())
    val image: StateFlow<List<String>> = _image.asStateFlow()

    fun imageToUrl(files: List<MultipartBody.Part>) {
        viewModelScope.launch {
            val response = imageRepository.imageToUrl(files, "users")

            if (response.isSuccessful) {
                response.body()?.let {
                    _image.value = it
                }
            }
        }
    }

    fun goToGallery(){
        viewModelScope.launch {
            _events.emit(MainEvent.GoToGallery)
        }
    }

}