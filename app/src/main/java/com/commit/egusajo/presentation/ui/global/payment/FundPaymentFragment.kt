package com.commit.egusajo.presentation.ui.global.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentFundPaymentBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.MainViewModel
import com.commit.egusajo.presentation.ui.PaymentState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FundPaymentFragment: BaseFragment<FragmentFundPaymentBinding>(R.layout.fragment_fund_payment) {

    private val viewModel: FundPaymentViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()
    private val args: FundPaymentFragmentArgs by navArgs()
    private val fundId by lazy { args.fundId }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        viewModel.setFundId(fundId)
        viewModel.getFundDetail()
        initEventObserver()
        initPaymentStateObserver()
    }

    private fun initEventObserver(){
        repeatOnStarted {
            viewModel.events.collect{
                when(it){
                    is FundPaymentEvents.GoToBootPay -> {
                        parentViewModel.openBootPay(
                            presentName = "[이거사조] ${it.data.name} 님의 생일모금 (상품 : ${it.data.presentName})",
                            presentId = it.data.presentId,
                            price = it.price
                        )
                    }

                    is FundPaymentEvents.NavigateBack -> findNavController().navigateUp()
                }
            }
        }
    }
    
    private fun initPaymentStateObserver(){
        repeatOnStarted { 
            parentViewModel.paymentState.collect{
                when(it){
                    is PaymentState.Success -> viewModel.participate()
                    is PaymentState.Error -> showCustomToast(it.msg)
                    else -> {}
                }
            }
        }
    }


}