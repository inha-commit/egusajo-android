package com.commit.egusajo.presentation.ui.mypage.friend

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentMyFriendBinding
import com.commit.egusajo.presentation.LoadingState
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.mypage.friend.adapter.MyFriendAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFriendFragment : BaseFragment<FragmentMyFriendBinding>(R.layout.fragment_my_friend) {

    private val viewModel: MyFriendViewModel by viewModels()
    private var loadingState = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.rvFriends.adapter = MyFriendAdapter()
        initEventObserver()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFollowerList()
    }

    private fun initEventObserver(){
        repeatOnStarted {
            viewModel.events.collect{
                when(it){
                    is MyFriendEvents.ShowSnackMessage -> showCustomSnack(binding.btnFollower, it.msg)
                    is MyFriendEvents.ShowLoading -> showLoading(requireContext())
                    is MyFriendEvents.DismissLoading -> dismissLoading()
                }
            }
        }
    }
}

@BindingAdapter("friendButton")
fun bindFriendButton(btn: AppCompatButton, state: Boolean) {
    if (state) {
        btn.setBackgroundResource(R.drawable.shape_grey3fill_nostroke_radius10)
        btn.setTextColor(ContextCompat.getColor(btn.context, R.color.black))
    } else {
        btn.setBackgroundResource(R.drawable.shape_lightgreyfill_nostroke_radius10)
        btn.setTextColor(ContextCompat.getColor(btn.context, R.color.es_grey7))
    }
}

@BindingAdapter("followButton")
fun bindFollowButton(btn: AppCompatButton, isFollowing: Boolean) {
    if (isFollowing) {
        btn.text = "언팔로우"
        btn.setTextColor(Color.BLACK)
        btn.setBackgroundResource(R.drawable.shape_lightgreyfill_nostroke_radius10)
    } else {
        btn.text = "팔로우"
        btn.setTextColor(Color.RED)
        btn.setBackgroundResource(R.drawable.shape_grey3fill_nostroke_radius10)
    }
}

