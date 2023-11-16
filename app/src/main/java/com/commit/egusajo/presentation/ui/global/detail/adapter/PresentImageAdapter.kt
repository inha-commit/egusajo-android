package com.commit.egusajo.presentation.ui.global.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.commit.egusajo.R
import com.commit.egusajo.databinding.ItemPresentImageBinding

class PresentImageAdapter(private val data: List<String>) :RecyclerView.Adapter<PresentImageViewHolder>() {

    override fun onBindViewHolder(holder: PresentImageViewHolder, position: Int) {
        holder.bind(data[position])
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

    override fun getItemCount(): Int = data.size

}

class PresentImageViewHolder(private val binding: ItemPresentImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String) {
        Glide.with(binding.root.context)
            .load(item)
            .error(R.drawable.icon_profile)
            .into(binding.ivPresentImg)
    }
}