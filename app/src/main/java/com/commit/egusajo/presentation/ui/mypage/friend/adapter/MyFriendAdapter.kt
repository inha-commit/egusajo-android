package com.commit.egusajo.presentation.ui.mypage.friend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.commit.egusajo.databinding.ItemFriendBinding
import com.commit.egusajo.presentation.ui.mypage.friend.model.UiFriendData
import com.commit.egusajo.util.DefaultDiffUtil

class MyFriendAdapter :
    ListAdapter<UiFriendData, MyFriendViewHolder>(DefaultDiffUtil<UiFriendData>()) {

    override fun onBindViewHolder(holder: MyFriendViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFriendViewHolder {
        return MyFriendViewHolder(
            ItemFriendBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class MyFriendViewHolder(private val binding: ItemFriendBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiFriendData) {
        binding.item = item
        binding.btnFollowUnfollow.setOnClickListener {
            item.followOrUnfollow(item.isFollowing, item.id)
        }
    }

}