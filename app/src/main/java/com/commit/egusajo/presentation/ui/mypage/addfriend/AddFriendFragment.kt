package com.commit.egusajo.presentation.ui.mypage.addfriend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentAddFriendBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.mypage.addfriend.adapter.SearchFriendAdapter
import com.commit.egusajo.presentation.ui.mypage.friend.adapter.MyFriendAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFriendFragment: BaseFragment<FragmentAddFriendBinding>(R.layout.fragment_add_friend) {

    private val viewModel: AddFriendViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.rvFriends.adapter = SearchFriendAdapter()
        binding.rvFriends.itemAnimator = null
        initEventObserve()
    }

    private fun initEventObserve(){
        repeatOnStarted {
            viewModel.events.collect{
                when(it){
                    is AddFriendEvents.ShowSnackMessage -> showCustomSnack(binding.etSearch, it.msg)
                }
            }
        }
    }

}