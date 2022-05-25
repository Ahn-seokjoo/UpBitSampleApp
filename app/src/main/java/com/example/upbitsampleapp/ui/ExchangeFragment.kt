package com.example.upbitsampleapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.upbitsampleapp.R
import com.example.upbitsampleapp.base.BaseFragment
import com.example.upbitsampleapp.databinding.FragmentExchangeBinding
import com.example.upbitsampleapp.viewmodel.ExchangeViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExchangeFragment : BaseFragment<FragmentExchangeBinding>(R.layout.fragment_exchange) {
    private val exchangeViewModel: ExchangeViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()
    private val exchangeRecyclerViewAdapter by lazy { ExchangeRecyclerViewAdapter(exchangeViewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = exchangeViewModel
            lifecycleOwner = viewLifecycleOwner
            recyclerview.adapter = exchangeRecyclerViewAdapter
        }
        exchangeViewModel.getAllCoinList("KRW")

        onObserve()
        initChangeLanguage()
        initAnotherCoin()
    }

    private fun initAnotherCoin() {
        binding.KRW.setOnClickListener {
            exchangeViewModel.getAllCoinList("KRW")
        }
        binding.BTC.setOnClickListener {
            exchangeViewModel.getAllCoinList("BTC")
        }
        binding.USDT.setOnClickListener {
            exchangeViewModel.getAllCoinList("USDT")
        }
    }

    private fun initChangeLanguage() {
        binding.koreanSort.setOnClickListener {
            exchangeViewModel.changeCoinNameLanguage()
        }
    }

    private fun onObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            exchangeViewModel.coinResult.collectLatest {
                exchangeRecyclerViewAdapter.submitList(it)
            }
        }
    }


    override fun onDestroyView() {
        compositeDisposable.dispose()
        super.onDestroyView()
    }
}
