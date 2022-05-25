package com.example.upbitsampleapp.repository

import com.example.upbitsampleapp.entities.dto.MarketItem
import com.example.upbitsampleapp.entities.dto.MarketTickerItem
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketApi {
    @GET("v1/market/all")
    suspend fun getMarket(@Query("isDetails") isDetails: Boolean = false): List<MarketItem>

    @GET("v1/ticker/")
    suspend fun getTickerDataList(@Query("markets") markets: String): List<MarketTickerItem>
}
