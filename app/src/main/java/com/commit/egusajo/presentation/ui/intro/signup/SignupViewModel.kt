package com.commit.egusajo.presentation.ui.intro.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commit.egusajo.data.model.NickCheckRequest
import com.commit.egusajo.data.model.SignupRequest
import com.commit.egusajo.data.repository.IntroRepository
import com.commit.egusajo.presentation.ui.intro.SnsId
import com.commit.egusajo.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val introRepository: IntroRepository) :
    ViewModel() {

    val name = MutableStateFlow("")
    val nick = MutableStateFlow("")
    val birth = MutableStateFlow("")

    init {
        checkNick()
    }

    val isDataReady = combine(name, nick, birth) { name, nick, birth ->
        name.isNotBlank()
                && nick.isNotBlank()
                && birth.isNotBlank()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        false
    )

    private fun checkNick() {
        nick.onEach {
            Log.d(TAG, it)
            val response = introRepository.checkNick(NickCheckRequest(it))

            if (response.isSuccessful) {

            } else {

            }
        }.launchIn(viewModelScope)
    }

    fun signup() {

        viewModelScope.launch {
            val response =
                introRepository.signup(
                    SignupRequest(
                        snsId = SnsId.snsId,
                        nickname =  nick.value,
                        birthday = birth.value
                    )
                )

            if (response.isSuccessful) {

            } else {

            }

        }
    }

}