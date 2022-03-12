package com.example.upbitsampleapp.repository

import com.example.upbitsampleapp.entities.dto.Market
import com.example.upbitsampleapp.entities.dto.MarketTicker
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ExchangeRepositoryImpl @Inject constructor(private val marketApi: MarketApi) : ExchangeRepository {
    override fun getMarketList(): Observable<Market> = marketApi.getMarket()

    override fun getTickerDataList(nameList: List<String>): Observable<MarketTicker> = marketApi.getTickerDataList(nameList)
}
