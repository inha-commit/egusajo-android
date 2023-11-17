package com.commit.egusajo.presentation.ui.mypage.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentMypageBinding
import com.commit.egusajo.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val viewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.getMyInfo()
        initEventObserver()
    }

    private fun initEventObserver(){
        repeatOnStarted {
            viewModel.events.collect{
                when(it){
                    is MyPageEvents.NavigateToMyFriend -> findNavController().toMyFriend()
                    is MyPageEvents.NavigateToMyParticipateFund -> findNavController().toMyParticipateFund()
                    is MyPageEvents.NavigateToMyFund -> findNavController().toMyFund()
                    is MyPageEvents.NavigateToEditProfile -> findNavController().toEditProfile()
                }
            }
        }
    }

    private fun NavController.toMyFriend(){
        val action = MyPageFragmentDirections.actionMypageFragmentToMyFriendFragment()
        this.navigate(action)
    }

    private fun NavController.toMyFund(){
        val action = MyPageFragmentDirections.actionMypageFragmentToMyFundFragment()
        this.navigate(action)
    }

    private fun NavController.toMyParticipateFund(){
        val action = MyPageFragmentDirections.actionMypageFragmentToMyParticipateFundFragment()
        this.navigate(action)
    }

    private fun NavController.toEditProfile(){
        val action = MyPageFragmentDirections.actionMypageFragmentToEditProfileFragment()
        this.navigate(action)
    }
}