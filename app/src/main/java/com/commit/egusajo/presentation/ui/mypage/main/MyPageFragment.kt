package com.commit.egusajo.presentation.ui.mypage.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentMypageBinding
import com.commit.egusajo.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val viewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.getMyInfo()
    }
}