package com.example.upbitsampleapp.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.upbitsampleapp.entities.CoinData
import com.example.upbitsampleapp.entities.dto.MarketTicker
import java.text.DecimalFormat

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("bindCurrentPrice")
    fun TextView.bindCurrentPrice(market: CoinData) {
        this.text = when {
            market.engName.endsWith("KRW") -> {
                DecimalFormat("#,###.####").format(market.currentPrice.toDouble())
            }
            market.engName.endsWith("BTC") -> {
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
            market.engName.endsWith("KRW") -> {
                "${DecimalFormat("###,###").format(market.tradePrice.toDouble() / 1000000)}백만"
            }
            market.engName.endsWith("BTC") -> {
                DecimalFormat("###,##0.000").format(market.tradePrice.toDouble())
            }
            else -> {
                DecimalFormat("##,###,##0.000").format(market.tradePrice.toDouble())
            }
        }
    }

    @JvmStatic
    @BindingAdapter(*arrayOf("bindPerKRW", "bitcoin"), requireAll = false)
    fun TextView.bindPerKRW(market: CoinData, bitcoin: NonNullLiveData<MutableList<MarketTicker.MarketTickerItem>>) {
        this.text = when {
            market.engName.endsWith("KRW") -> {
                ""
            }
            market.engName.endsWith("BTC") -> {
                val krwPrice = market.currentPrice.toDouble() * bitcoin.value.first().tradePrice
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
    @BindingAdapter("bindData")
    fun RecyclerView.bindData(list: LiveData<MutableList<CoinData>>?) {
        list?.value?.toList().let {
            (adapter as ListAdapter<Any, RecyclerView.ViewHolder>).submitList(it)
        }
    }
}
