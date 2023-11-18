package com.commit.egusajo.presentation.ui.intro.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentSignupBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.MainActivity
import com.commit.egusajo.presentation.ui.intro.IntroViewModel
import com.commit.egusajo.util.DateType
import com.commit.egusajo.util.showCalendarDatePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(R.layout.fragment_signup) {

    private val viewModel: SignupViewModel by viewModels()
    private val parentViewModel: IntroViewModel by activityViewModels()

    private val navArgs: SignupFragmentArgs by navArgs()
    private val account by lazy{ navArgs.account }
    private val bank by lazy { navArgs.bank }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pvm = parentViewModel
        binding.vm = viewModel
        viewModel.setAccountInfo(account, bank)
        setBirthBtnListener()
        initStateObserver()
        initEventsObserver()
    }

    private fun setBirthBtnListener() {
        binding.tilBirth.setEndIconOnClickListener {
            showCalendarDatePicker(parentFragmentManager, DateType.BIRTH_DAY) {
                viewModel.setBirth(it)
            }
        }
    }

    private fun initStateObserver() {

        repeatOnStarted {
            parentViewModel.profileImg.collect {
                if (it.isNotBlank()) {
                    viewModel.setProfileImg(it)
                }
            }
        }

    }

    private fun initEventsObserver(){
        repeatOnStarted {
            viewModel.events.collect{
                when(it){
                    is SignupEvents.NavigateToMainActivity -> {
                        startActivity(Intent(requireContext(), MainActivity::class.java).setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        ))
                    }
                    is SignupEvents.ShowToastMessage -> showCustomToast(it.msg)
                }
            }
        }
    }
}

