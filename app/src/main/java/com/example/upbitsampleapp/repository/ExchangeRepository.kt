package com.example.upbitsampleapp.repository

import com.example.upbitsampleapp.entities.dto.MarketItem
import com.example.upbitsampleapp.entities.dto.MarketTickerItem
import com.example.upbitsampleapp.entities.dto.WebSocketTickerResult
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {
    suspend fun getMarketList(): List<MarketItem>
    suspend fun getTickerDataList(nameList: String): List<MarketTickerItem>
    fun startCoinFlow(type: String)
    fun stopCoinFlow()
    fun emitChannelData(): Flow<WebSocketTickerResult.WebSocketTickerResultItem>
}
