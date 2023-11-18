package com.commit.egusajo.presentation.ui.global.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.commit.egusajo.databinding.ItemFundParticipationBinding
import com.commit.egusajo.presentation.ui.global.detail.model.ParticipateData
import com.commit.egusajo.util.DefaultDiffUtil

class FundListAdapter: ListAdapter<ParticipateData,FundListViewHolder>(DefaultDiffUtil<ParticipateData>()) {

    override fun onBindViewHolder(holder: FundListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundListViewHolder {
        return FundListViewHolder(
            ItemFundParticipationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class FundListViewHolder(private val binding: ItemFundParticipationBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: ParticipateData){
        binding.item = item
    }
}
