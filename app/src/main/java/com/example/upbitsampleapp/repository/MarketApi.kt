package com.example.upbitsampleapp.repository

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketApi {
    @GET("v1/market/all")
    fun getMarket(@Query("isDetails") isDetails: Boolean = false): Single<Market>

    @GET("v1/ticker/")
    fun getTickerDataList(@Query("markets") markets: String): Single<MarketTicker>
}
