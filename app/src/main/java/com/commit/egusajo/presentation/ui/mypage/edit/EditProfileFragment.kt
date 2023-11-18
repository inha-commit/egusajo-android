package com.commit.egusajo.presentation.ui.mypage.edit

import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentEditProfileBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.google.android.material.materialswitch.MaterialSwitch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment: BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    private val viewModel: EditProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        viewModel.getOriginInfo()
    }
}

@BindingAdapter("thumbColor")
fun bindThumbColor(switch: MaterialSwitch, state: Boolean){
    if(state){
        switch.thumbTintList = switch.context.getColorStateList(R.color.es_light_yellow)
    } else {
        switch.thumbTintList = switch.context.getColorStateList(R.color.es_grey8)
    }
}
