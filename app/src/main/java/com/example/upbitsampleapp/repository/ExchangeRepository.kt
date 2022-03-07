package com.example.upbitsampleapp.repository

import io.reactivex.rxjava3.core.Single

interface ExchangeRepository {
    fun getMarketList(): Single<Market>
    fun getTickerDataList(nameList: String): Single<MarketTicker>
}
