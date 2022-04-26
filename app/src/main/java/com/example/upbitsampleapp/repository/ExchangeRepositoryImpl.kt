package com.example.upbitsampleapp.repository

import com.example.upbitsampleapp.entities.dto.MarketItem
import com.example.upbitsampleapp.entities.dto.MarketTickerItem
import com.example.upbitsampleapp.entities.dto.WebSocketTickerResult
import com.example.upbitsampleapp.util.UpBitWebSocket
import io.reactivex.rxjava3.core.Single
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

    override fun getMarketList(): Single<List<MarketItem>> {
        return marketApi.getMarket()
    }

    override fun getTickerDataList(nameList: List<String>): Single<List<MarketTickerItem>> {
        return marketApi.getTickerDataList(nameList)
    }


}
