package com.example.upbitsampleapp.repository

import com.example.upbitsampleapp.entities.dto.Market
import com.example.upbitsampleapp.entities.dto.MarketTicker
import io.reactivex.rxjava3.core.Single

interface ExchangeRepository {
    fun getMarketList(): Single<Market>
    fun getTickerDataList(nameList: String): Single<MarketTicker>
}
