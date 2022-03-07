package com.example.upbitsampleapp.repository

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ExchangeRepositoryImpl @Inject constructor(private val marketApi: MarketApi) : ExchangeRepository {
    override fun getMarketList(): Single<Market> = marketApi.getMarket()

    override fun getTickerDataList(nameList: String): Single<MarketTicker> = marketApi.getTickerDataList(nameList)
}
