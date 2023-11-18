package com.commit.egusajo.presentation.ui.mypage.participate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.commit.egusajo.databinding.ItemMyParticipateBinding
import com.commit.egusajo.databinding.ItemMyParticipateDateBinding
import com.commit.egusajo.presentation.ui.mypage.participate.model.UiParticipateData
import com.commit.egusajo.util.DefaultDiffUtil

class MyParticipateAdapter : ListAdapter<UiParticipateData, ParticipateViewHolder>(DefaultDiffUtil<UiParticipateData>()) {

    companion object{
        const val DATA = 1000
        const val DATE = 1001
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipateViewHolder {
        return when(viewType){
            DATA-> {
                 MyParticipateViewHolder(
                    ItemMyParticipateBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            DATE -> {
                DateLabelViewHolder(
                    ItemMyParticipateDateBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: ParticipateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).viewType){
            "DATA" -> DATA
            "DATE" -> DATE
            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

}

abstract class ParticipateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){
    abstract fun bind(item: UiParticipateData)
}


class MyParticipateViewHolder(private val binding: ItemMyParticipateBinding) :
    ParticipateViewHolder(binding) {

    override fun bind(item: UiParticipateData) {
        binding.item = item
        binding.root.setOnClickListener {
            item.onItemClickListener(item.fundId)
        }
    }
}

class DateLabelViewHolder(private val binding: ItemMyParticipateDateBinding) :
    ParticipateViewHolder(binding) {

    override fun bind(item: UiParticipateData) {
        binding.tvDate.text = item.participateDate
    }
}

