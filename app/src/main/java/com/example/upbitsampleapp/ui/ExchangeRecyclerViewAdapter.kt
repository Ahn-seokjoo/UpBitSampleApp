package com.example.upbitsampleapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.upbitsampleapp.databinding.RecyclerviewItemBinding
import com.example.upbitsampleapp.entities.CoinData

class ExchangeRecyclerViewAdapter : ListAdapter<CoinData, ExchangeViewHolder>(ExchangeDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeViewHolder {
        return ExchangeViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ExchangeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class ExchangeViewHolder(private val parent: ViewGroup) : RecyclerView.ViewHolder(
    RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
) {
    private val binding: RecyclerviewItemBinding = DataBindingUtil.bind(itemView) ?: throw IllegalStateException("fail to bind")
    fun bind(item: CoinData) {
        binding.apply {
            market = item
            executePendingBindings()
        }
    }

}

object ExchangeDiffUtil : DiffUtil.ItemCallback<CoinData>() {
    override fun areItemsTheSame(oldItem: CoinData, newItem: CoinData): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: CoinData, newItem: CoinData): Boolean {
        return oldItem == newItem
    }
}


