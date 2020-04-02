package com.currencyconverter.data.dto

data class Currency(
    var country: String = "",
    var base: Boolean = true,
    var value: String = "0"
)
