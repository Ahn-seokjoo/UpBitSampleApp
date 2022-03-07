package com.example.upbitsampleapp.entities

data class CoinData(
    val korName: String,
    val engName: String,
    val currentPrice: Int,
    val perYesterday: Double,
    val tradePrice: Double
)
