package com.commit.egusajo.presentation.ui.global.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentFundPaymentBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.MainActivity
import com.commit.egusajo.presentation.ui.MainViewModel
import com.tosspayments.paymentsdk.BuildConfig
import com.tosspayments.paymentsdk.PaymentWidget

class FundPaymentFragment: BaseFragment<FragmentFundPaymentBinding>(R.layout.fragment_fund_payment) {

    private val viewModel: FundPaymentViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTossPay.setOnClickListener {
            parentViewModel.tossPayStart()
        }
    }
}