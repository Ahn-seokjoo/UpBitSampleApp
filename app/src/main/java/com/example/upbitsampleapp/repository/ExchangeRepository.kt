package com.example.upbitsampleapp.repository

import com.example.upbitsampleapp.entities.dto.MarketItem
import com.example.upbitsampleapp.entities.dto.MarketTickerItem
import io.reactivex.rxjava3.core.Single

interface ExchangeRepository {
    fun getMarketList(): Single<List<MarketItem>>
    fun getTickerDataList(nameList: List<String>): Single<List<MarketTickerItem>>
}
