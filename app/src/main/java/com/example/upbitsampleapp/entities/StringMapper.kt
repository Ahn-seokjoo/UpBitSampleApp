package com.example.upbitsampleapp.entities

import com.example.upbitsampleapp.repository.Market
import com.example.upbitsampleapp.repository.MarketTicker

fun Market.nameMapper(category: String): String = with(StringBuilder()) {
    this@nameMapper.forEach {
        when (category) {
            "KRW" -> {
                if (it.market.startsWith("KRW-")) {
                    this.append("${it.market},")
                }
            }
            "BTC" -> {
                if (it.market.startsWith("BTC-")) {
                    this.append("${it.market},")
                }
            }
            "USDT" -> {
                if (it.market.startsWith("USDT")) {
                    this.append("${it.market},")
                }
            }

        }

    }
    this.deleteCharAt(this.lastIndex).toString()
}

fun MarketTicker.MarketTickerItem.toCoinData(allMarketList: MutableList<Market.MarketItem>) = CoinData(
    korName = allMarketList.find {
        it.market == this.market
    }!!.koreanName,
    engName = getEnglishMarket(allMarketList.find {
        it.market == this.market
    }!!.market),
    currentPrice = tradePrice.toInt(),
    perYesterday = (prevClosingPrice / tradePrice),
    tradePrice = accTradeVolume24h
)

fun getEnglishMarket(market: String): String {
    var stringData = market.split("-")
    return "${stringData[1]}/${stringData[0]}"
}
