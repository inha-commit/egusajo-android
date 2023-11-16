package com.commit.egusajo.presentation.ui.global.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.commit.egusajo.databinding.ItemPresentImageBinding
import com.commit.egusajo.util.DefaultDiffUtil

class PresentImageAdapter() :ListAdapter<String, PresentImageViewHolder>(DefaultDiffUtil<String>()) {

    override fun onBindViewHolder(holder: PresentImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresentImageViewHolder {
        return PresentImageViewHolder(
            ItemPresentImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}

class PresentImageViewHolder(private val binding: ItemPresentImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String) {
        binding.url = item
    }
}