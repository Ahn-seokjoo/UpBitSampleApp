package com.example.upbitsampleapp.entities.dto


import com.google.gson.annotations.SerializedName

data class WebsocketResult(
    @SerializedName("code")
    val market: String,
    @SerializedName("orderbook_units")
    val orderbookUnits: List<OrderbookUnit>,
    @SerializedName("stream_type")
    val streamType: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("total_ask_size")
    val totalAskSize: Double,
    @SerializedName("total_bid_size")
    val totalBidSize: Double
) {
    data class OrderbookUnit(
        @SerializedName("ask_price")
        val askPrice: Double,
        @SerializedName("ask_size")
        val askSize: Double,
        @SerializedName("bid_price")
        val bidPrice: Double,
        @SerializedName("bid_size")
        val bidSize: Double
    )
}
