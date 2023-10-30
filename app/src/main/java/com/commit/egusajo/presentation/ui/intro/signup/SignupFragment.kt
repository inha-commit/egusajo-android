package com.commit.egusajo.presentation.ui.intro.signup

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentSignupBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.intro.IntroViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(R.layout.fragment_signup) {

    private val viewModel: SignupViewModel by viewModels()
    private val parentViewModel: IntroViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pvm = parentViewModel
        binding.vm = viewModel
        initObserver()
    }

    private fun initObserver(){

        repeatOnStarted {
            parentViewModel.profileImg.collect{
                if(it.isNotBlank()){
                    viewModel.setProfileImg(it)
                }
            }
        }

        repeatOnStarted {
            viewModel.uiState.collect{
                when(it.signUp){
                    is SignupState.Success -> parentViewModel.goToMainActivity()
                    is SignupState.Error -> showCustomToast(it.signUp.msg)
                    else -> {}
                }
            }
        }
    }

}