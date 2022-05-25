package com.example.upbitsampleapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upbitsampleapp.entities.CoinData
import com.example.upbitsampleapp.entities.dto.MarketItem
import com.example.upbitsampleapp.entities.dto.MarketTickerItem
import com.example.upbitsampleapp.entities.getMarketList
import com.example.upbitsampleapp.entities.toCoinData
import com.example.upbitsampleapp.repository.ExchangeRepository
import com.example.upbitsampleapp.util.NonNullLiveData
import com.example.upbitsampleapp.util.NonNullMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val exchangeRepository: ExchangeRepository,
) : ViewModel() {

    private val _coinNameStatus = NonNullMutableLiveData(KOREAN)
    val coinNameStatus: NonNullLiveData<Boolean> = _coinNameStatus

    private val coinList = MutableStateFlow<List<MarketItem>>(emptyList())

    private val _bitCoin = NonNullMutableLiveData<MarketTickerItem>(MarketTickerItem())
    val bitCoin: NonNullLiveData<MarketTickerItem> = _bitCoin

    private val _coinResult = MutableStateFlow<List<CoinData>>(mutableListOf())
    val coinResult: StateFlow<List<CoinData>> = _coinResult.asStateFlow()

    fun getAllCoinList(category: String) {
        viewModelScope.launch {
            coinList.value = exchangeRepository.getMarketList()
            val result = exchangeRepository.getTickerDataList(coinList.value.getMarketList()).map {
                if (it.market == "KRW-BTC") {
                    _bitCoin.value = it
                }
                it.toCoinData(coinList.value, _coinNameStatus.value)
            }
            _coinResult.value = result.filter { it.market.endsWith(category) }
            startCollectingCoinList(category)
        }
    }

    private fun startCollectingCoinList(type: String) {
        // 웹소켓 통신을 요구
        viewModelScope.launch {
            exchangeRepository.startCoinFlow(type)
            exchangeRepository.emitChannelData().collectLatest { webSocket ->
                _coinResult.update { list ->
                    val toCoinData = webSocket.toCoinData(coinList.value, _coinNameStatus.value)
                    list.map { coinData ->
                        if (coinData.market == toCoinData.market) {
                            toCoinData
                        } else {
                            coinData
                        }
                    }
                }
            }
        }
    }

    fun changeCoinNameLanguage() {
        _coinNameStatus.value = !_coinNameStatus.value

        if (_coinNameStatus.value) {
            _coinResult.update { coinResult ->
                coinResult.map { market ->
                    market.copy(check = coinNameStatus.value)
                }
            }
        } else {
            _coinResult.update { coinResult ->
                coinResult.map { market ->
                    market.copy(check = coinNameStatus.value)
                }
            }
        }
    }

    override fun onCleared() {
        exchangeRepository.stopCoinFlow()
        super.onCleared()
    }

    companion object {
        const val KOREAN = true
        const val ENGLISH = false
    }
}
