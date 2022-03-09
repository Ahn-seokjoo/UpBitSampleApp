package com.example.upbitsampleapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.upbitsampleapp.entities.CoinData
import com.example.upbitsampleapp.entities.dto.Market
import com.example.upbitsampleapp.entities.nameMapper
import com.example.upbitsampleapp.entities.toCoinData
import com.example.upbitsampleapp.repository.ExchangeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val exchangeRepository: ExchangeRepository
) : ViewModel() {
    private val allMarketTickerDataList = mutableListOf<CoinData>()
    private val allMarketList = mutableListOf<Market.MarketItem>()

    private val _marketResult = MutableLiveData<MutableList<CoinData>>()
    val marketResult: LiveData<MutableList<CoinData>> = _marketResult

    fun getCoinData(category: String) {
        exchangeRepository.getMarketList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { market ->
                market.forEach {
                    allMarketList.add(it)
                }
                market.nameMapper(category)
            }
            .subscribe({
                getTickerDataList(it)
            }) {
                Log.d("TAG", "getMarket 실패: ${it.message}")
            }
    }

    private fun getTickerDataList(nameList: String) {
        exchangeRepository.getTickerDataList(nameList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val list = mutableListOf<CoinData>()
                it.forEach {
                    list.add(it.toCoinData(allMarketList))
                }
                list
            }
            .doAfterSuccess {
                _marketResult.value = allMarketTickerDataList
            }
            .subscribe({
                allMarketTickerDataList.clear()
                allMarketTickerDataList.addAll(it)
            }) {
                Log.d("TAG", "getAllCoin 실패: ${it.message}")
            }
    }

}
