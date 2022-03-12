package com.example.upbitsampleapp.repository

import com.example.upbitsampleapp.entities.dto.Market
import com.example.upbitsampleapp.entities.dto.MarketTicker
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketApi {
    @GET("v1/market/all")
    fun getMarket(@Query("isDetails") isDetails: Boolean = false): Observable<Market>

    @GET("v1/ticker/")
    fun getTickerDataList(@Query("markets") markets: List<String>): Observable<MarketTicker>
}
