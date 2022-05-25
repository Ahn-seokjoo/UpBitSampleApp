package com.example.upbitsampleapp.entities

import java.math.BigDecimal

data class CoinData(
    val korName: String,
    val engName: String,
    val market: String,
    val currentPrice: BigDecimal,
    val perYesterday: Double,
    val tradePrice: BigDecimal,
    val check: Boolean
)
