package com.commit.egusajo.presentation.ui.intro.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentAccountVerificationBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.intro.adapter.BankAdapter

class AccountVerificationFragment :
    BaseFragment<FragmentAccountVerificationBinding>(R.layout.fragment_account_verification) {

    private val viewModel: AccountVerificationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.rvBank.adapter = BankAdapter()
        viewModel.getBankList()
        initStateObserver()
        initEventsObserver()
    }

    private fun initStateObserver() {
        repeatOnStarted {
            viewModel.uiState.collect {

            }
        }
    }

    private fun initEventsObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is AccountVerificationEvents.NavigateToSignup -> findNavController().toSignup(
                        it.bank,
                        it.account
                    )
                }
            }
        }
    }

    private fun NavController.toSignup(bank: String, account: String) {
        val action =
            AccountVerificationFragmentDirections.actionAccountVerificationFragmentToSignupFragment(
                account,
                bank
            )
        this.navigate(action)
    }


}