package com.example.upbitsampleapp.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.upbitsampleapp.R
import com.example.upbitsampleapp.base.BaseFragment
import com.example.upbitsampleapp.databinding.FragmentExchangeBinding
import com.example.upbitsampleapp.viewmodel.ExchangeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeFragment : BaseFragment<FragmentExchangeBinding>(R.layout.fragment_exchange) {
    private val exchangeViewModel: ExchangeViewModel by viewModels()
    private lateinit var _binding: FragmentExchangeBinding
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view) ?: throw IllegalStateException("fail to bind")
        val recyclerViewAdapter = ExchangeRecyclerViewAdapter()
        binding.recyclerview.adapter = recyclerViewAdapter

        if (savedInstanceState == null) {
            exchangeViewModel.getCoinData("KRW")
        }
        exchangeViewModel.marketResult.observe(viewLifecycleOwner) {
            recyclerViewAdapter.submitList(it.toList())
        }
        initClickListener()

    }

    private fun initClickListener() {
        binding.KRW.setOnClickListener {
            exchangeViewModel.getCoinData("KRW")
        }
        binding.BTC.setOnClickListener {
            exchangeViewModel.getCoinData("BTC")
        }
        binding.USDT.setOnClickListener {
            exchangeViewModel.getCoinData("USDT")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}
