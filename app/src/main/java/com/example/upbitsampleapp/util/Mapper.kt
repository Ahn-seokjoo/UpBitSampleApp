package com.example.upbitsampleapp.entities

import com.example.upbitsampleapp.entities.dto.MarketTickerItem

fun MarketTickerItem.toCoinData() = CoinData(
    korName = getName(market, true),
    engName = getName(market, false),
    market = getEnglishMarket(market),
    currentPrice = tradePrice.toBigDecimal(),
    perYesterday = (tradePrice / prevClosingPrice),
    tradePrice = accTradePrice24h.toBigDecimal()
)

fun getName(market: String, status: Boolean) = if (status) {
    when {
        market.startsWith("KRW") -> {
            KRW.getMarket(market).kor
        }
        market.startsWith("BTC") -> {
            BTC.getMarket(market).kor
        }
        else -> {
            USDT.getMarket(market).kor
        }
    }
} else {
    when {
        market.startsWith("KRW") -> {
            KRW.getMarket(market).eng
        }
        market.startsWith("BTC") -> {
            BTC.getMarket(market).eng
        }
        else -> {
            USDT.getMarket(market).eng
        }
    }
}


fun getEnglishMarket(market: String): String {
    val stringData = market.split("-")
    return "${stringData[1]}/${stringData[0]}"
}

fun String.undoGetEnglishMarket(): String {
    val stringData = this.split("/")
    return "${stringData[1]}-${stringData[0]}"
}
