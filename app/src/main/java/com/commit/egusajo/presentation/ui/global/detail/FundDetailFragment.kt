package com.commit.egusajo.presentation.ui.global.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentFundDetailBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.global.detail.adapter.PresentImageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FundDetailFragment: BaseFragment<FragmentFundDetailBinding>(R.layout.fragment_fund_detail) {

    private val viewModel: FundDetailViewModel by viewModels()

    private val args: FundDetailFragmentArgs by navArgs()
    private val fundId by lazy { args.fundId }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.rvPresentImg.adapter = PresentImageAdapter()
        viewModel.getFundDetail(fundId)
        PagerSnapHelper().attachToRecyclerView(binding.rvPresentImg)
    }

}