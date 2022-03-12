package com.example.upbitsampleapp.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.upbitsampleapp.R
import com.example.upbitsampleapp.base.BaseFragment
import com.example.upbitsampleapp.databinding.FragmentExchangeBinding
import com.example.upbitsampleapp.viewmodel.ExchangeViewModel
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ExchangeFragment : BaseFragment<FragmentExchangeBinding>(R.layout.fragment_exchange) {
    private val exchangeViewModel: ExchangeViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()
    private val exchangeRecyclerViewAdapter = ExchangeRecyclerViewAdapter()
    private lateinit var _binding: FragmentExchangeBinding
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view) ?: throw IllegalStateException("fail to bind")

        binding.apply {
            vm = exchangeViewModel
            recyclerview.adapter = exchangeRecyclerViewAdapter
        }

        if (savedInstanceState == null) {
            exchangeViewModel.getCoinData("KRW")
        }

        initClickListener()
        searchCoinName()
    }

    private fun initClickListener() {
        with(binding) {
            KRW.setOnClickListener {
                exchangeViewModel.getCoinData("KRW")
            }
            BTC.setOnClickListener {
                exchangeViewModel.getCoinData("BTC")
            }
            USDT.setOnClickListener {
                exchangeViewModel.getCoinData("USDT")
            }
        }
    }

    private fun searchCoinName() {
        binding.searchCoinOrSymbol.textChanges()
            .debounce(1000L, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .map { text ->
                if (text.isNotEmpty()) {
                    exchangeViewModel.coinResult.value //non nullable livedata 사용해보자
                        .filter {
                            it.korName.contains(text)
                        }
                } else {
                    exchangeViewModel.coinResult.value
                }
            }
            .subscribe {
                exchangeRecyclerViewAdapter.submitList(it)
            }.addTo(compositeDisposable)
    }

    override fun onDestroyView() {
        compositeDisposable.dispose()
        binding.unbind()
        super.onDestroyView()
    }
}
