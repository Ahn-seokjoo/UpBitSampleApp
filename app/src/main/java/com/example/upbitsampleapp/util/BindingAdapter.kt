package com.example.upbitsampleapp.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.upbitsampleapp.entities.CoinData
import com.example.upbitsampleapp.entities.dto.MarketTickerItem
import java.text.DecimalFormat

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("bindCurrentPrice")
    fun TextView.bindCurrentPrice(market: CoinData) {
        this.text = when {
            market.market.endsWith("KRW") -> {
                DecimalFormat("#,###.####").format(market.currentPrice.toDouble())
            }
            market.market.endsWith("BTC") -> {
                DecimalFormat("#,##0.00000000").format(market.currentPrice.toDouble())
            }
            else -> {
                DecimalFormat("#,##0.000").format(market.currentPrice.toDouble())
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bindPerYesterday")
    fun TextView.bindPerYesterday(perYesterday: Double) {
        this.text = "${DecimalFormat("#0.00").format(perYesterday * 100 - 100)}%"
    }

    @JvmStatic
    @BindingAdapter("bindTradePrice")
    fun TextView.bindTradePrice(market: CoinData) {
        this.text = when {
            market.market.endsWith("KRW") -> {
                "${DecimalFormat("###,###").format(market.tradePrice.toDouble() / 1000000)}백만"
            }
            market.market.endsWith("BTC") -> {
                DecimalFormat("###,##0.000").format(market.tradePrice.toDouble())
            }
            else -> {
                DecimalFormat("##,###,##0.000").format(market.tradePrice.toDouble())
            }
        }
    }

    @JvmStatic
    @BindingAdapter(*arrayOf("bindPerKRW", "bitcoin"), requireAll = true)
    fun TextView.bindPerKRW(market: CoinData, bitcoin: NonNullLiveData<MarketTickerItem>) {
        this.text = when {
            market.market.endsWith("KRW") -> {
                ""
            }
            market.market.endsWith("BTC") -> {
                val krwPrice = market.currentPrice.toDouble() * bitcoin.value.tradePrice
                if (krwPrice.toInt() > 100) { // 비트코인 가격 x 현재가
                    "${DecimalFormat("#,###,##0").format(krwPrice)} KRW"
                } else {
                    "${DecimalFormat("##.00").format(krwPrice)} KRW"
                }
            }
            else -> { // 현재가 x 환율
                val krwPrice = market.currentPrice.toDouble() * 1237.11
                if (krwPrice < 100) {
                    "${DecimalFormat("#.###").format(krwPrice)} KRW"
                } else {
                    "${DecimalFormat("#,###,###").format(krwPrice)} KRW"
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("coinNameText")
    fun TextView.coinNameText(status: NonNullLiveData<Boolean>) {
        this.text = if (status.value) {
            "한글명"
        } else {
            "영문명"
        }
    }

    @JvmStatic
    @BindingAdapter(*arrayOf("bindName", "bindStatus"), requireAll = true)
    fun TextView.bindName(market: CoinData, status: NonNullLiveData<Boolean>) {
        this.text = if (status.value) {
            market.korName
        } else {
            market.engName
        }
    }

}
