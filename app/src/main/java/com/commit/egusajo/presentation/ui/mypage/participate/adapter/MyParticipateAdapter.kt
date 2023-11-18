package com.commit.egusajo.presentation.ui.mypage.participate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.commit.egusajo.databinding.ItemMyParticipateBinding
import com.commit.egusajo.presentation.ui.mypage.participate.model.UiParticipateData
import com.commit.egusajo.util.DefaultDiffUtil

class MyParticipateAdapter : ListAdapter<UiParticipateData, MyParticipateViewHolder>(DefaultDiffUtil<UiParticipateData>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  MyParticipateViewHolder {
        return  MyParticipateViewHolder(
            ItemMyParticipateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder:  MyParticipateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}


class MyParticipateViewHolder(private val binding: ItemMyParticipateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UiParticipateData) {
        binding.item = item
        binding.root.setOnClickListener {
            item.onItemClickListener(item.fundId)
        }
    }

}