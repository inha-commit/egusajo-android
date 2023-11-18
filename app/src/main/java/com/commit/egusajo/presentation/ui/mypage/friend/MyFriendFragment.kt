package com.commit.egusajo.presentation.ui.mypage.friend

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import com.commit.egusajo.R
import com.commit.egusajo.databinding.FragmentMyFriendBinding
import com.commit.egusajo.presentation.base.BaseFragment
import com.commit.egusajo.presentation.ui.mypage.friend.adapter.MyFriendAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFriendFragment : BaseFragment<FragmentMyFriendBinding>(R.layout.fragment_my_friend) {

    private val viewModel: MyFriendViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.rvFriends.adapter = MyFriendAdapter()
        viewModel.getFollowerList()
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

