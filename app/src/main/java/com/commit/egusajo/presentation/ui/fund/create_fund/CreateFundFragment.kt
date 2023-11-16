package com.commit.egusajo.presentation.ui.fund.create_fund

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentCreateFundBinding
import com.commit.egusajo.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateFundFragment: BaseFragment<FragmentCreateFundBinding>(R.layout.fragment_create_fund) {

    private val viewModel: CreateFundViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}