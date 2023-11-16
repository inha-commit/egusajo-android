package com.commit.egusajo.presentation.ui.fund

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentFundBinding
import com.commit.egusajo.presentation.base.BaseFragment

class FundFragment : BaseFragment<FragmentFundBinding>(R.layout.fragment_fund) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(R.raw.ballon)
            .override(150,150)
            .into(binding.ivHeader)
    }
}