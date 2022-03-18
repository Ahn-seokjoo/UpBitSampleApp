package com.example.upbitsampleapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.upbitsampleapp.databinding.RecyclerviewItemBinding
import com.example.upbitsampleapp.entities.CoinData
import com.example.upbitsampleapp.viewmodel.ExchangeViewModel

class ExchangeRecyclerViewAdapter(private val viewModel: ExchangeViewModel) : ListAdapter<CoinData, ExchangeViewHolder>(ExchangeDiffUtil) {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeViewHolder {
        return ExchangeViewHolder(parent, viewModel)
    }

    override fun onBindViewHolder(holder: ExchangeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).market.hashCode().toLong()
    }

}

class ExchangeViewHolder(private val parent: ViewGroup, private val viewModel: ExchangeViewModel) : RecyclerView.ViewHolder(
    RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
) {
    private val binding: RecyclerviewItemBinding = DataBindingUtil.bind(itemView) ?: throw IllegalStateException("fail to bind")

    fun bind(item: CoinData) {
        binding.apply {
            market = item
            vm = viewModel
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


