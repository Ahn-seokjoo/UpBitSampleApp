package com.example.upbitsampleapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.upbitsampleapp.entities.CoinData
import com.example.upbitsampleapp.entities.dto.Market
import com.example.upbitsampleapp.entities.getMarketList
import com.example.upbitsampleapp.entities.toCoinData
import com.example.upbitsampleapp.repository.ExchangeRepository
import com.example.upbitsampleapp.util.NonNullLiveData
import com.example.upbitsampleapp.util.NonNullMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val exchangeRepository: ExchangeRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _marketResult = NonNullMutableLiveData(mutableListOf<CoinData>())
    val marketResult: NonNullLiveData<MutableList<CoinData>> = _marketResult

    fun getCoinData(category: String) {
        exchangeRepository.getMarketList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { market ->
                Observable.fromIterable(market)
            }
            .filter { marketItem ->
                marketItem.market.startsWith(category)
            }
            .toList()
            .subscribe({ list ->
                getTickerDataList(list)
            }) {
                Log.d("TAG", "getMarket 실패: ${it.message}")
            }.addTo(compositeDisposable)
    }

    private fun getTickerDataList(nameList: MutableList<Market.MarketItem>) {
        exchangeRepository.getTickerDataList(nameList.getMarketList())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                Observable.fromIterable(it)
            }
            .map {
                it.toCoinData(nameList)
            }
            .toList()
            .subscribe({
                _marketResult.value = it
            }) {
                Log.d("TAG", "getAllCoin 실패: ${it.message}")
            }.addTo(compositeDisposable)

    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}
