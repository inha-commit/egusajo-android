package com.commit.egusajo.presentation.ui.mypage.edit

import android.os.Bundle
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentEditProfileBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.MainViewModel
import com.commit.egusajo.util.showCalendarDatePicker
import com.google.android.material.materialswitch.MaterialSwitch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    private val viewModel: EditProfileViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.pvm = parentViewModel
        viewModel.getOriginInfo()
        setBirthBtnListener()
        initImageObserver()
        initEventObserver()
    }

    private fun setBirthBtnListener() {
        binding.tilBirth.setEndIconOnClickListener {
            showCalendarDatePicker(parentFragmentManager) {
                viewModel.setBirth(it)
            }
        }
    }

    private fun initImageObserver() {
        repeatOnStarted {
            parentViewModel.image.collect {
                viewModel.setProfileImg(it)
            }
        }
    }

    private fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is EditProfileEvents.NavigateToBack -> findNavController().navigateUp()
                    else -> {}
                }
            }
        }
    }

}

@BindingAdapter("thumbColor")
fun bindThumbColor(switch: MaterialSwitch, state: Boolean) {
    if (state) {
        switch.thumbTintList = switch.context.getColorStateList(R.color.es_light_yellow)
    } else {
        switch.thumbTintList = switch.context.getColorStateList(R.color.es_grey8)
    }
}
