package com.commit.egusajo.presentation.ui.fund

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentFundBinding
import com.commit.egusajo.presentation.base.BaseFragment

class FundFragment : BaseFragment<FragmentFundBinding>(R.layout.fragment_fund) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setGif()
        setBtnListener()
    }

    private fun setGif() {
        Glide.with(this)
            .load(R.raw.ballon)
            .override(150, 150)
            .into(binding.ivHeader)
    }

    private fun setBtnListener() {
        binding.btnCreateFund.setOnClickListener {
            findNavController().toCreateFund()
        }
    }

    private fun NavController.toCreateFund() {
        val action = FundFragmentDirections.actionFundFragmentToCreateFundFragment()
        this.navigate(action)
    }
}