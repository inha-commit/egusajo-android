package com.commit.egusajo.presentation.ui.mypage.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.commit.egusajo.R
import com.commit.egusajo.app.App
import com.commit.egusajo.databinding.FragmentMypageBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.intro.IntroActivity
import com.commit.egusajo.util.Constants
import com.commit.egusajo.util.Constants.TAG
import com.kakao.sdk.user.UserApiClient
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
                    is MyPageEvents.Logout -> kakaoUnlink()
                    is MyPageEvents.ShowSnackMessage -> showCustomSnack(binding.ivProfile, it.msg)
                    is MyPageEvents.NavigateToAddFriend -> findNavController().toAddFriend()
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

    private fun NavController.toAddFriend(){
        val action = MyPageFragmentDirections.actionMypageFragmentToAddFriendFragment()
        navigate(action)
    }

    private fun kakaoUnlink(){

        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(TAG, "연결 끊기 실패", error)
            }
            else {
                Log.d(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                App.sharedPreferences.edit()
                    .remove(Constants.X_ACCESS_TOKEN)
                    .remove(Constants.X_REFRESH_TOKEN)
                    .apply()
                val intent = Intent(requireContext(),IntroActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}