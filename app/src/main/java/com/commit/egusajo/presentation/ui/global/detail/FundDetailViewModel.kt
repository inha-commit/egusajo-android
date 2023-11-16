package com.commit.egusajo.presentation.ui.global.detail

import androidx.lifecycle.ViewModel
import com.commit.egusajo.data.repository.FundRepository
import com.commit.egusajo.presentation.ui.global.detail.model.UiFundDetailData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class FundDetailUiState(
    val fundDetail: UiFundDetailData = UiFundDetailData()
)

@HiltViewModel
class FundDetailViewModel @Inject constructor(
    private val fundRepository: FundRepository
): ViewModel() {


}