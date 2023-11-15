package com.commit.egusajo.presentation.ui.intro.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.commit.egusajo.databinding.ItemBankBinding
import com.commit.egusajo.util.DefaultDiffUtil

class BankAdapter(
    private val itemClickListener: BankItemClickListener
) : ListAdapter<String, BankViewHolder>(DefaultDiffUtil<String>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        return BankViewHolder(
            ItemBankBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }
}

class BankViewHolder(
    private val binding: ItemBankBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: String,
        itemClickListener: BankItemClickListener
    ) {
        binding.tvBank.text = item
        binding.tvBank.setOnClickListener {
            itemClickListener.onClick(binding.tvBank,item)
        }
    }

}
