package com.example.upbitsampleapp.entities.dto


import com.google.gson.annotations.SerializedName

data class MarketItem(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("korean_name")
    val koreanName: String,
    @SerializedName("market")
    val market: String
)
