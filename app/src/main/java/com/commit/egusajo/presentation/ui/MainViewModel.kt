package com.commit.egusajo.presentation.ui

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


sealed class GallerySelectType{
    object MultiSelect: GallerySelectType()
    object SingleSelect: GallerySelectType()
}

sealed class MainEvent {
    object GoToGallery: MainEvent()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _gallerySelectType = MutableStateFlow<GallerySelectType>(GallerySelectType.MultiSelect)
    val gallerySelectType: StateFlow<GallerySelectType> = _gallerySelectType.asStateFlow()

    private val _events = MutableSharedFlow<MainEvent>()
    val events: SharedFlow<MainEvent> = _events.asSharedFlow()

    private val _images = MutableStateFlow(listOf<String>())
    val images: StateFlow<List<String>> = _images.asStateFlow()

    private val _image = MutableStateFlow("")
    val image: StateFlow<String> = _image.asStateFlow()

    fun imagesToUrls(files: List<MultipartBody.Part>) {
        viewModelScope.launch {
            val response = imageRepository.imageToUrl(files, "users")

            if (response.isSuccessful) {
                response.body()?.let {
                    _images.value = it
                }
            }
        }
    }

    fun imageToUrl(file: MultipartBody.Part) {
        viewModelScope.launch {
            val response = imageRepository.imageToUrl(listOf(file), "users")

            if (response.isSuccessful) {
                response.body()?.let {
                    _image.value = it[0]
                }
            }
        }
    }

    fun goToMultiSelectGallery(){
        _gallerySelectType.value = GallerySelectType.MultiSelect
        viewModelScope.launch {
            _events.emit(MainEvent.GoToGallery)
        }
    }

    fun goToSingleSelectGallery(){
        _gallerySelectType.value = GallerySelectType.SingleSelect
        viewModelScope.launch{
            _events.emit(MainEvent.GoToGallery)
        }
    }


}