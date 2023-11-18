package com.commit.egusajo.presentation.ui

import androidx.navigation.NavController
import com.commit.egusajo.MainNavDirections

internal fun NavController.toFundDetail(fundId: Int) {
    val action = MainNavDirections.actionGlobalToFundDetailFragment(fundId)
    this.navigate(action)
}