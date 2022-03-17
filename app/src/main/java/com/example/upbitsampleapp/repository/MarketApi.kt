package com.example.upbitsampleapp.repository

import com.example.upbitsampleapp.entities.dto.MarketItem
import com.example.upbitsampleapp.entities.dto.MarketTickerItem
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketApi {
    @GET("v1/market/all")
    fun getMarket(@Query("isDetails") isDetails: Boolean = false): Single<List<MarketItem>>

    @GET("v1/ticker/")
    fun getTickerDataList(@Query("markets") markets: List<String>): Single<List<MarketTickerItem>>
}
