package com.example.upbitsampleapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upbitsampleapp.entities.*
import com.example.upbitsampleapp.entities.dto.MarketTickerItem
import com.example.upbitsampleapp.repository.ExchangeRepository
import com.example.upbitsampleapp.util.NonNullLiveData
import com.example.upbitsampleapp.util.NonNullMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
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

    private val _bitCoin = NonNullMutableLiveData<MarketTickerItem>(MarketTickerItem())
    val bitCoin: NonNullLiveData<MarketTickerItem> = _bitCoin

    private val _coinResult = MutableStateFlow<List<CoinData>>(mutableListOf())
    val coinResult: StateFlow<List<CoinData>> = _coinResult.asStateFlow()

    fun firstGetCoinList(category: String) {
        viewModelScope.launch {
            val marketList = exchangeRepository.getMarketList().getMarketList()
            val result = exchangeRepository.getTickerDataList(marketList).map {
                if (it.market == "KRW-BTC") {
                    _bitCoin.value = it
                }
                it.toCoinData()
            }
            _coinResult.value = result.filter {
                it.market.endsWith(category)
            }
        }
        // 보여주는 데이터 필터링좀 해야될듯듯
    }

    // 첫통신으로 데이터 채우고, 리스트에 넣은 다음
    // 밑에걸로 distinct 걸어서 계속 업데이트 ?
    private fun startCollectingCoinList(type: String) {
        // 웹소켓 통신을 요구
        viewModelScope.launch {
            exchangeRepository.startCoinFlow(type)
            exchangeRepository.emitChannelData().collectLatest { webSocket ->
                _coinResult.update { list ->
                    // set으로 수정.
//                    _coinResult.value.set()
//                    _coinResult.value.replace(webSocket.toCoinData())
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
