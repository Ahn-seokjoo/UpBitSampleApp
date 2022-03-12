package com.example.upbitsampleapp.repository

import com.example.upbitsampleapp.entities.dto.Market
import com.example.upbitsampleapp.entities.dto.MarketTicker
import io.reactivex.rxjava3.core.Observable

interface ExchangeRepository {
    fun getMarketList(): Observable<Market>
    fun getTickerDataList(nameList: List<String>): Observable<MarketTicker>
}
