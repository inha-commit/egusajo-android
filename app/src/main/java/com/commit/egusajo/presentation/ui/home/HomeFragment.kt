package com.commit.egusajo.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentHomeBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.home.adapter.HomeFundAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.rvFund.adapter = HomeFundAdapter()
    }
}