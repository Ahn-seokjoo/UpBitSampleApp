package com.example.upbitsampleapp.repository

import com.example.upbitsampleapp.entities.dto.MarketItem
import com.example.upbitsampleapp.entities.dto.MarketTickerItem
import com.example.upbitsampleapp.entities.dto.WebSocketTickerResult
import com.example.upbitsampleapp.util.UpBitWebSocket
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExchangeRepositoryImpl @Inject constructor(
    private val marketApi: MarketApi,
    private val upBitWebSocket: UpBitWebSocket,
    private val channel: Channel<WebSocketTickerResult.WebSocketTickerResultItem>
) : ExchangeRepository {

    override fun startCoinFlow(type: String) {
        upBitWebSocket.start(type)
    }

    override fun stopCoinFlow() {
        upBitWebSocket.stop()
    }

    override fun emitChannelData() = flow {
        for (data in channel) {
            emit(data)
        }
    }

    override suspend fun getMarketList(): List<MarketItem> {
        return marketApi.getMarket()
    }

    override suspend fun getTickerDataList(nameList: String): List<MarketTickerItem> {
        return marketApi.getTickerDataList(nameList)
    }
}
