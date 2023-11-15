package com.commit.egusajo.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.commit.egusajo.databinding.ItemMyfriendFundBinding
import com.commit.egusajo.presentation.ui.home.model.Fund
import com.commit.egusajo.util.DefaultDiffUtil

class HomeFundAdapter : ListAdapter<Fund, HomeFundViewHolder>(DefaultDiffUtil<Fund>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFundViewHolder {
        return HomeFundViewHolder(
            ItemMyfriendFundBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeFundViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}


class HomeFundViewHolder(private val binding: ItemMyfriendFundBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Fund) {
        binding.item = item
        binding.root.setOnClickListener {
            item.onItemClickListener(item.fundId)
        }
    }

}