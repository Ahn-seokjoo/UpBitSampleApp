package com.example.upbitsampleapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.upbitsampleapp.entities.*
import com.example.upbitsampleapp.entities.dto.MarketItem
import com.example.upbitsampleapp.entities.dto.MarketTickerItem
import com.example.upbitsampleapp.repository.ExchangeRepository
import com.example.upbitsampleapp.util.NonNullLiveData
import com.example.upbitsampleapp.util.NonNullMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val exchangeRepository: ExchangeRepository,
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _coinNameStatus = NonNullMutableLiveData(KOREAN)
    val coinNameStatus: NonNullLiveData<Boolean> = _coinNameStatus

    private val _bitCoin = NonNullMutableLiveData<MutableList<MarketTickerItem>>(mutableListOf())
    val bitCoin: NonNullMutableLiveData<MutableList<MarketTickerItem>> = _bitCoin

    private val _coinResult = NonNullMutableLiveData(listOf<CoinData>())
    val coinResult: NonNullLiveData<List<CoinData>> = _coinResult

    fun getCoinData(category: String) {
        exchangeRepository.getMarketList()
            .subscribeOn(Schedulers.io())
            .map { market ->
                market.filter { marketItem ->
                    marketItem.market.startsWith(category)
                }
            }
            .flatMap {
                exchangeRepository.getTickerDataList(it.getMarketList())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .map { marketTicker ->
                marketTicker.map {
                    if (it.market == "KRW-BTC") {
                        _bitCoin.value = mutableListOf(it)
                    }
                    it.toCoinData(_coinNameStatus.value)
                }
            }
            .subscribe({
                _coinResult.value = it
            }) {
                Log.d("TAG", "getCoinData: ${it.message}")
            }.addTo(compositeDisposable)
    }

    fun coinLanguageSwitch() {
        _coinNameStatus.value = !_coinNameStatus.value
    }

    fun changeNameLanguage(): List<CoinData> {
        if (_coinNameStatus.value) {
            return _coinResult.value.map { market ->
                when {
                    market.market.endsWith("KRW") -> {
                        changeNameLanguage(KRW.getMarket(market.market.undoGetEnglishMarket()).kor, market)
                    }
                    market.market.endsWith("BTC") -> {
                        changeNameLanguage(BTC.getMarket(market.market.undoGetEnglishMarket()).kor, market)
                    }
                    else -> {
                        changeNameLanguage(USDT.getMarket(market.market.undoGetEnglishMarket()).kor, market)
                    }
                }
            }
        } else {
            return _coinResult.value.map { market ->
                when {
                    market.market.endsWith("KRW") -> {
                        changeNameLanguage(KRW.getMarket(market.market.undoGetEnglishMarket()).eng, market)
                    }
                    market.market.endsWith("BTC") -> {
                        changeNameLanguage(BTC.getMarket(market.market.undoGetEnglishMarket()).eng, market)
                    }
                    else -> {
                        changeNameLanguage(USDT.getMarket(market.market.undoGetEnglishMarket()).eng, market)
                    }
                }
            }
        }
    }

    private fun changeNameLanguage(coinName: String, coinData: CoinData): CoinData {
        return coinData.copy(name = coinName)
    }

    private fun List<MarketItem>.getMarketList(): List<String> {
        return this.map {
            it.market
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    companion object {
        const val KOREAN = true
        const val ENGLISH = false
    }
}
