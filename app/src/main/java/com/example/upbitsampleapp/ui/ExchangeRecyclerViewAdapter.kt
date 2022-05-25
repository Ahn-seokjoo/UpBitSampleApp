package com.example.upbitsampleapp.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.upbitsampleapp.entities.CoinData
import com.example.upbitsampleapp.viewmodel.ExchangeViewModel

class ExchangeRecyclerViewAdapter(private val viewModel: ExchangeViewModel) : ListAdapter<CoinData, ExchangeViewHolder>(exchangeDiffUtil) {
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

    companion object {
        private val exchangeDiffUtil = object : DiffUtil.ItemCallback<CoinData>() {
            override fun areItemsTheSame(oldItem: CoinData, newItem: CoinData): Boolean {
                return oldItem.market == newItem.market
            }

            override fun areContentsTheSame(oldItem: CoinData, newItem: CoinData): Boolean {
                return oldItem == newItem
            }
        }
    }
}






