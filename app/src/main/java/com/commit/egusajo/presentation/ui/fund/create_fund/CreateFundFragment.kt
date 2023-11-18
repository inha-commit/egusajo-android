package com.commit.egusajo.presentation.ui.fund.create_fund

import android.os.Bundle
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentCreateFundBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.MainViewModel
import com.commit.egusajo.util.DateType
import com.commit.egusajo.util.showCalendarDatePicker
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateFundFragment : BaseFragment<FragmentCreateFundBinding>(R.layout.fragment_create_fund) {

    private val viewModel: CreateFundViewModel by viewModels()
    private val parentViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        setDateBtnListener()
        initEventObserver()
        imagesObserver()
    }

    private fun setDateBtnListener() {
        binding.tilDDay.setEndIconOnClickListener {
            showCalendarDatePicker(parentFragmentManager, DateType.DEADLINE) {
                viewModel.setDeadline(it)
            }
        }
    }

    private fun initEventObserver() {
        repeatOnStarted {
            viewModel.events.collect {
                when (it) {
                    is CreateFundEvent.GoToGallery -> parentViewModel.goToMultiSelectGallery()
                    is CreateFundEvent.NavigateToBack -> findNavController().navigateUp()
                    else -> {}
                }
            }
        }
    }

    private fun imagesObserver() {
        repeatOnStarted {
            parentViewModel.images.collect {
                if (it.isNotEmpty()) {
                    viewModel.setImages(it)
                }
            }
        }
    }
}