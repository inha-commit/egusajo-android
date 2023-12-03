package com.commit.egusajo.presentation.ui.mypage.addfriend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.commit.egusajo.databinding.ItemFriendBinding
import com.commit.egusajo.presentation.ui.mypage.friend.model.UiFriendData
import com.commit.egusajo.util.DefaultDiffUtil

class SearchFriendAdapter : ListAdapter<UiFriendData, MyFriendViewHolder>(diffCallback) {

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<UiFriendData>(){
            override fun areItemsTheSame(oldItem: UiFriendData, newItem: UiFriendData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UiFriendData, newItem: UiFriendData): Boolean {
                return oldItem == newItem
            }
        }
    }

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