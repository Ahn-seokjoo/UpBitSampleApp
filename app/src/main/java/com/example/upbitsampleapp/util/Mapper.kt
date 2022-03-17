package com.example.upbitsampleapp.entities

import android.util.Log
import com.example.upbitsampleapp.entities.dto.MarketTickerItem

fun MarketTickerItem.toCoinData(status: Boolean) = CoinData(
    name = getName(market, status),
    market = getEnglishMarket(market),
    currentPrice = tradePrice.toBigDecimal(),
    perYesterday = (tradePrice / prevClosingPrice),
    tradePrice = accTradePrice24h.toBigDecimal()
)

fun getName(market: String, status: Boolean) = if (status) {
    when {
        market.startsWith("KRW") -> {
            Log.d("TAG", "getName: $market")
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
