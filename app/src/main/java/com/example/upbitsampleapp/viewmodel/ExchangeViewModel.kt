package com.example.upbitsampleapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val exchangeRepository: ExchangeRepository,
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _coinNameStatus = NonNullMutableLiveData(KOREAN)
    val coinNameStatus: NonNullLiveData<Boolean> = _coinNameStatus

    private val _bitCoin = NonNullMutableLiveData<MutableList<MarketTickerItem>>(mutableListOf())
    val bitCoin: NonNullLiveData<MutableList<MarketTickerItem>> = _bitCoin

    private val _coinResult = MutableStateFlow<MutableList<CoinData>>(mutableListOf())
    val coinResult: StateFlow<MutableList<CoinData>> = _coinResult.asStateFlow()

    // 코루틴으로
    fun firstGetCoinList(category: String) {
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
                    it.toCoinData()
                }
            }
            .doOnSuccess {
                startCollectingCoinList(category)
            }
            .subscribe({
                _coinResult.value = it.toMutableList()
            }) {
                Log.d("TAG", "getCoinData: ${it.message}")
            }.addTo(compositeDisposable)
    }

    // 첫통신으로 데이터 채우고, 리스트에 넣은 다음
    // 밑에걸로 distinct 걸어서 계속 업데이트 ?
    private fun startCollectingCoinList(type: String) {
        // 웹소켓 통신을 요구
        viewModelScope.launch {
            exchangeRepository.startCoinFlow(type)
            exchangeRepository.emitChannelData().collectLatest { webSocket ->
                _coinResult.update { list ->
                    val toCoinData = webSocket.toCoinData()
                    list.map { coinData ->
                        if (coinData.market == toCoinData.market) {
                            toCoinData
                        } else {
                            coinData
                        }
                    }.toMutableList()
                }
            }
        }
    }

    fun changeCoinNameLanguage() {
        _coinNameStatus.value = !_coinNameStatus.value

        if (_coinNameStatus.value) {
            _coinResult.value = _coinResult.value.map { market ->
                when {
                    market.market.endsWith("KRW") -> {
                        copyKorName(KRW.getMarket(market.market.undoGetEnglishMarket()).kor, market)
                    }
                    market.market.endsWith("BTC") -> {
                        copyKorName(BTC.getMarket(market.market.undoGetEnglishMarket()).kor, market)
                    }
                    else -> {
                        copyKorName(USDT.getMarket(market.market.undoGetEnglishMarket()).kor, market)
                    }
                }
            }.toMutableList()
        } else {
            _coinResult.value = _coinResult.value.map { market ->
                when {
                    market.market.endsWith("KRW") -> {
                        copyEngName(KRW.getMarket(market.market.undoGetEnglishMarket()).eng, market)
                    }
                    market.market.endsWith("BTC") -> {
                        copyEngName(BTC.getMarket(market.market.undoGetEnglishMarket()).eng, market)
                    }
                    else -> {
                        copyEngName(USDT.getMarket(market.market.undoGetEnglishMarket()).eng, market)
                    }
                }
            }.toMutableList()
        }
    }


    private fun copyKorName(coinName: String, coinData: CoinData): CoinData {
        return coinData.copy(korName = coinName)
    }

    private fun copyEngName(coinName: String, coinData: CoinData): CoinData {
        return coinData.copy(engName = coinName)
    }

    private fun List<MarketItem>.getMarketList(): List<String> {
        return this.map {
            it.market
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        exchangeRepository.stopCoinFlow()
        super.onCleared()
    }

    companion object {
        const val KOREAN = true
        const val ENGLISH = false
    }
}
