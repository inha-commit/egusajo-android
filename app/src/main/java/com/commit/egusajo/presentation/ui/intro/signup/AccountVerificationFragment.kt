package com.commit.egusajo.presentation.ui.intro.signup

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentAccountVerificationBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.intro.adapter.BankAdapter
import com.commit.egusajo.presentation.ui.intro.adapter.BankItemClickListener

class AccountVerificationFragment :
    BaseFragment<FragmentAccountVerificationBinding>(R.layout.fragment_account_verification),
    BankItemClickListener {

    private val viewModel: AccountVerificationViewModel by viewModels()
    private lateinit var curView: TextView
    private var isCurViewSet = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.rvBank.adapter = BankAdapter(this)
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

    override fun onClick(view: TextView, bank: String) {
        if (isCurViewSet) {
            curView.setTextColor(Color.BLACK)
            curView.setBackgroundResource(R.drawable.shape_red1fill_nostroke_radius20)
        } else {
            isCurViewSet = true
        }

        view.setTextColor(Color.WHITE)
        view.setBackgroundResource(R.drawable.shape_pinkfill_nostroke_radius20)
        curView = view
        viewModel.setBank(bank)
    }


}