package com.example.upbitsampleapp.repository

import com.example.upbitsampleapp.entities.dto.MarketItem
import com.example.upbitsampleapp.entities.dto.MarketTickerItem
import com.example.upbitsampleapp.entities.dto.WebSocketTickerResult
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {
    fun getMarketList(): Single<List<MarketItem>>
    fun getTickerDataList(nameList: List<String>): Single<List<MarketTickerItem>>
    fun startCoinFlow(type: String)
    fun stopCoinFlow()
    fun emitChannelData(): Flow<WebSocketTickerResult.WebSocketTickerResultItem>
}
