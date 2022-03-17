package com.example.upbitsampleapp.repository

import com.example.upbitsampleapp.entities.dto.MarketItem
import com.example.upbitsampleapp.entities.dto.MarketTickerItem
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ExchangeRepositoryImpl @Inject constructor(private val marketApi: MarketApi) : ExchangeRepository {
    override fun getMarketList(): Single<List<MarketItem>> = marketApi.getMarket()

    override fun getTickerDataList(nameList: List<String>): Single<List<MarketTickerItem>> = marketApi.getTickerDataList(nameList)
}
