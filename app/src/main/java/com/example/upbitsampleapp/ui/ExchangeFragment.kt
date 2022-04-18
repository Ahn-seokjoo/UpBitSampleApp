package com.example.upbitsampleapp.ui

import android.os.Bundle
import android.view.View
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
    private val exchangeRecyclerViewAdapter by lazy { ExchangeRecyclerViewAdapter(exchangeViewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = exchangeViewModel
            lifecycleOwner = viewLifecycleOwner
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
                if (text.isNullOrEmpty()) {
                    exchangeViewModel.coinResult.value
                } else {
                    val check = text.toString().replace(" ", "").firstCharacterToUpperCase()
                    exchangeViewModel.coinResult.value
                        .filter {
                            it.korName.contains(check)
                                    || it.engName.contains(check)
                        }
                }
            }
            .subscribe {
                exchangeRecyclerViewAdapter.submitList(it)
            }.addTo(compositeDisposable)
    }

    private fun String.firstCharacterToUpperCase(): String {
        val result = StringBuilder()
        for ((index, data) in this.withIndex()) {
            if (index == 0) result.append(data.uppercase()) else result.append(data.lowercase())
        }
        return result.toString()
    }

    override fun onDestroyView() {
        compositeDisposable.dispose()
        super.onDestroyView()
    }
}
