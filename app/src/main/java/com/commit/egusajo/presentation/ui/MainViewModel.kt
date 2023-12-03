package com.commit.egusajo.presentation.ui

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


sealed class GallerySelectType{
    object MultiSelect: GallerySelectType()
    object SingleSelect: GallerySelectType()
}

sealed class MainEvent {
    object GoToGallery: MainEvent()
    data class OpenBootPay(
        val presentName: String,
        val presentId: String,
        val price: Int
    ): MainEvent()
    data class ShowSnackMessage(val msg: String): MainEvent()
    data class ShowToastMessage(val msg: String): MainEvent()
}

sealed class PaymentState{
    object Empty: PaymentState()
    object Success: PaymentState()
    data class Error(val msg: String): PaymentState()
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

    private val _paymentState = MutableStateFlow<PaymentState>(PaymentState.Empty)
    val paymentState: StateFlow<PaymentState> = _paymentState.asStateFlow()

    fun imagesToUrls(files: List<MultipartBody.Part>) {
        viewModelScope.launch {
            imageRepository.imageToUrl(files, "users").let{
                when(it){
                    is BaseState.Success -> {
                        _images.value = it.body
                    }

                    is BaseState.Error -> {
                        _events.emit(MainEvent.ShowSnackMessage(it.msg))
                    }
                }
            }
        }
    }

    fun imageToUrl(file: MultipartBody.Part) {
        viewModelScope.launch {
            imageRepository.imageToUrl(listOf(file), "users").let{
                when(it){
                    is BaseState.Success -> {
                        _image.value = it.body[0]
                    }

                    is BaseState.Error -> {
                        _events.emit(MainEvent.ShowSnackMessage(it.msg))
                    }
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

    fun openBootPay(
        presentName: String,
        presentId: String,
        price: Int
    ){
        viewModelScope.launch {
            _events.emit(MainEvent.OpenBootPay(
                presentName,
                presentId,
                price
            ))
        }
    }

    fun paymentState(state: PaymentState){
        _paymentState.value = state

        _paymentState.value = PaymentState.Empty
    }

    fun imagesCollected(){
        _images.value = listOf()
    }

    fun imageCollected(){
        _image.value = ""
    }


}