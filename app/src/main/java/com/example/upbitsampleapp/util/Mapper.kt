package com.example.upbitsampleapp.entities

import com.example.upbitsampleapp.entities.dto.MarketItem
import com.example.upbitsampleapp.entities.dto.MarketTickerItem
import com.example.upbitsampleapp.entities.dto.WebSocketTickerResult

fun WebSocketTickerResult.WebSocketTickerResultItem.toCoinData(marketList: List<MarketItem>) = CoinData(
    korName = getName(marketList, market, true) ?: "",
    engName = getName(marketList, market, false) ?: "",
    market = market.getEnglishMarket(),
    currentPrice = tradePrice.toBigDecimal(),
    perYesterday = (tradePrice / prevClosingPrice),
    tradePrice = accTradePrice24h.toBigDecimal(),
)

fun MarketTickerItem.toCoinData(marketList: List<MarketItem>) = CoinData(
    korName = getName(marketList, market, true) ?: "",
    engName = getName(marketList, market, false) ?: "",
    market = market.getEnglishMarket(),
    currentPrice = tradePrice.toBigDecimal(),
    perYesterday = (tradePrice / prevClosingPrice),
    tradePrice = accTradePrice24h.toBigDecimal(),
)

fun getName(marketList: List<MarketItem>, market: String, status: Boolean) = if (status) {
    when {
        market.startsWith("KRW") -> {
            marketList.find {
                it.market == market
            }?.koreanName
        }
        market.startsWith("BTC") -> {
            marketList.find {
                it.market == market
            }?.koreanName
        }
        else -> {
            marketList.find {
                it.market == market
            }?.koreanName
        }
    }
} else {
    when {
        market.startsWith("KRW") -> {
            marketList.find {
                it.market == market
            }?.englishName
        }
        market.startsWith("BTC") -> {
            marketList.find {
                it.market == market
            }?.englishName
        }
        else -> {
            marketList.find {
                it.market == market
            }?.englishName
        }
    }
}


fun String.getEnglishMarket(): String {
    val stringData = this.split("-")
    return "${stringData[1]}/${stringData[0]}"
}

fun String.undoGetEnglishMarket(): String {
    val stringData = this.split("/")
    return "${stringData[1]}-${stringData[0]}"
}

fun List<MarketItem>.getMarketList(): String {
    val marketList = this
    val result = StringBuilder().apply {
        marketList.map { marketItem ->
            append("${marketItem.market},")
        }
    }
    return result.removeSuffix(",").toString()
}
