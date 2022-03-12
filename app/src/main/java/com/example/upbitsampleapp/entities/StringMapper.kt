package com.example.upbitsampleapp.entities

import com.example.upbitsampleapp.entities.dto.Market
import com.example.upbitsampleapp.entities.dto.MarketTicker

fun MarketTicker.MarketTickerItem.toCoinData(allMarketList: MutableList<Market.MarketItem>) = CoinData(
    korName = allMarketList.first {
        it.market == this.market
    }.koreanName,
    engName = getEnglishMarket(allMarketList.first {
        it.market == this.market
    }.market),
    currentPrice = tradePrice.toBigDecimal(),
    perYesterday = (tradePrice / prevClosingPrice),
    tradePrice = accTradePrice24h.toBigDecimal()
)

fun getEnglishMarket(market: String): String {
    val stringData = market.split("-")
    return "${stringData[1]}/${stringData[0]}"
}

fun MutableList<Market.MarketItem>.getMarketList(): List<String> {
    val list = mutableListOf<String>()
    this.forEach { marketItem ->
        list.add(marketItem.market)
    }
    return list
}
