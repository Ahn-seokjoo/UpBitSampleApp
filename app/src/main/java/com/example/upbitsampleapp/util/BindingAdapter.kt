package com.example.upbitsampleapp.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.upbitsampleapp.entities.CoinData
import java.math.BigDecimal
import java.text.DecimalFormat

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("bindCurrentPrice")
    fun TextView.bindCurrentPrice(price: BigDecimal) {
        this.text = DecimalFormat("#,###.####").format(price.toDouble()).toString()
    }

    @JvmStatic
    @BindingAdapter("bindPerYesterday")
    fun TextView.bindPerYesterday(perYesterday: Double) {
        this.text = "${DecimalFormat("#.##").format(perYesterday * 100 - 100)}%"
    }

    @JvmStatic
    @BindingAdapter("bindTradePrice")
    fun TextView.bindTradePrice(tradePrice: BigDecimal) {
        this.text = "${DecimalFormat("###,###").format(tradePrice.toDouble() / 1000000)}백만"
    }

    @JvmStatic
    @BindingAdapter("bindData")
    fun RecyclerView.bindData(list: LiveData<MutableList<CoinData>>?) {
        list?.value?.toList().let {
            (adapter as ListAdapter<Any, RecyclerView.ViewHolder>).submitList(it)
        }
    }
}
