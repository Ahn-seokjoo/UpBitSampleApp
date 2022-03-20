package com.example.upbitsampleapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.upbitsampleapp.databinding.RecyclerviewItemBinding
import com.example.upbitsampleapp.entities.CoinData
import com.example.upbitsampleapp.viewmodel.ExchangeViewModel

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
