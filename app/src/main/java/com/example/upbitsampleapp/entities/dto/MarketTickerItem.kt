package com.example.upbitsampleapp.entities.dto


import com.google.gson.annotations.SerializedName

data class MarketTickerItem(
    @SerializedName("acc_trade_price")
    val accTradePrice: Double = 0.0,
    @SerializedName("acc_trade_price_24h")
    val accTradePrice24h: Double = 0.0,
    @SerializedName("acc_trade_volume")
    val accTradeVolume: Double = 0.0,
    @SerializedName("acc_trade_volume_24h")
    val accTradeVolume24h: Double = 0.0,
    @SerializedName("change")
    val change: String = "",
    @SerializedName("change_price")
    val changePrice: Double = 0.0,
    @SerializedName("change_rate")
    val changeRate: Double = 0.0,
    @SerializedName("high_price")
    val highPrice: Double = 0.0,
    @SerializedName("highest_52_week_date")
    val highest52WeekDate: String = "",
    @SerializedName("highest_52_week_price")
    val highest52WeekPrice: Double = 0.0,
    @SerializedName("low_price")
    val lowPrice: Double = 0.0,
    @SerializedName("lowest_52_week_date")
    val lowest52WeekDate: String = "",
    @SerializedName("lowest_52_week_price")
    val lowest52WeekPrice: Double = 0.0,
    @SerializedName("market")
    val market: String = "",
    @SerializedName("opening_price")
    val openingPrice: Double = 0.0,
    @SerializedName("prev_closing_price")
    val prevClosingPrice: Double = 0.0,
    @SerializedName("signed_change_price")
    val signedChangePrice: Double = 0.0,
    @SerializedName("signed_change_rate")
    val signedChangeRate: Double = 0.0,
    @SerializedName("timestamp")
    val timestamp: Long = 0,
    @SerializedName("trade_date")
    val tradeDate: String = "",
    @SerializedName("trade_date_kst")
    val tradeDateKst: String = "",
    @SerializedName("trade_price")
    val tradePrice: Double = 0.0,
    @SerializedName("trade_time")
    val tradeTime: String = "",
    @SerializedName("trade_time_kst")
    val tradeTimeKst: String = "",
    @SerializedName("trade_timestamp")
    val tradeTimestamp: Long = 0,
    @SerializedName("trade_volume")
    val tradeVolume: Double = 0.0
)
