package com.currencyconverter.data.dto

import com.google.gson.annotations.SerializedName

data class CurrenciesListResponse(
    @SerializedName("baseCurrency")
    val baseCurrency: String = "",
    @SerializedName("rates")
    val rates: HashMap<String, Double> = hashMapOf()
)